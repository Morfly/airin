package io.morfly.airin.plugin

import io.morfly.airin.Component
import io.morfly.airin.ComponentId
import io.morfly.airin.FeatureComponent
import io.morfly.airin.ModuleComponent
import io.morfly.airin.GradleModule
import io.morfly.airin.GradleProjectDecorator
import io.morfly.airin.dsl.AirinExtension
import io.morfly.airin.dsl.AirinProperties
import io.morfly.airin.plugin.task.MigrateProjectToBazelTask
import io.morfly.airin.plugin.task.MigrateRootToBazel
import io.morfly.airin.plugin.task.MigrateToBazelTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.configurationcache.extensions.capitalized
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.withType

const val AIRIN_TASK_GROUP = "Airin Bazel migration"

abstract class AirinGradlePlugin : Plugin<Project> {

    abstract val defaultProjectDecorator: Class<out GradleProjectDecorator>

    override fun apply(target: Project) {
        require(target.rootProject.path == target.path) {
            "Airin must be applied to the root project but was applied to ${target.path}!"
        }
        val inputs = target.extensions.create<AirinExtension>(AirinExtension.NAME)

        target.gradle.projectsEvaluated {
            if (!inputs.enabled) return@projectsEvaluated

            checkTargets(inputs)

            val inputProjects = inputs.targets.map(target::project)
            val components = prepareComponents(inputs.subcomponents)
            // TODO replace with null check instead of default
            val decoratorClass =
                if (inputs.projectDecorator != GradleProjectDecorator::class.java) inputs.projectDecorator
                else defaultProjectDecorator
            val decorator = inputs.objects.newInstance(decoratorClass)

            val projectCollector = ProjectDependencyCollector(inputs)
            val artifactCollector = ArtifactDependencyCollector(inputs)
            val transformer = DefaultProjectTransformer(
                components, inputs, decorator, artifactCollector
            )
            for (inputProject in inputProjects) {
                val allProjects = projectCollector.invoke(inputProject)
                    .filter { (path, _) -> path != target.path }

                for (project in allProjects.values) {
                    if (project.path in inputs.skippedProjects) continue
                    registerMigrateProjectToBazelTask(
                        target = project,
                        transformer = transformer
                    )
                }

                val rootTaskName = inputProject.buildRootTaskName()
                registerMigrateRootToBazel(
                    target = target,
                    name = rootTaskName,
                    projects = allProjects,
                    components = components,
                    transformer = transformer
                )

                registerMigrateToBazel(
                    target = inputProject,
                    root = target,
                    rootTaskName = rootTaskName,
                    allProjects = allProjects,
                )
            }
        }
    }

    private fun prepareComponents(
        components: Map<ComponentId, Component<GradleModule>>
    ): Map<ComponentId, ModuleComponent> {
        // Feature components shared with every package component
        val topLevelSharedFeatureComponents = components.values.asSequence()
            .filterIsInstance<FeatureComponent>()
            .onEach { it.shared = true }
            .associateBy { it.id }

        // Feature components shared only with shared package components
        val sharedFeatureComponents = components.values.asSequence()
            .filterIsInstance<ModuleComponent>()
            .flatMap { it.subcomponents.values }
            .filterIsInstance<FeatureComponent>()
            .filter { it.shared }
            .associateBy { it.id }

        // Package components with added feature components
        return components.values.asSequence()
            .filterIsInstance<ModuleComponent>()
            .onEach { it.subcomponents += topLevelSharedFeatureComponents }
            .onEach { if (it.shared) it.subcomponents += sharedFeatureComponents }
            .associateBy { it.id }
    }

    private fun registerMigrateProjectToBazelTask(
        target: Project,
        transformer: ProjectTransformer,
    ) {
        if (target.tasks.any { it.name == MigrateProjectToBazelTask.NAME }) return

        target.tasks.register<MigrateProjectToBazelTask>(MigrateProjectToBazelTask.NAME) {
            group = AIRIN_TASK_GROUP

            val (module, component) = transformer.invoke(target)

            this.component.set(component)
            this.module.set(module)
            this.outputFile.set(target.outputFile())
        }
    }

    private fun registerMigrateToBazel(
        target: Project,
        root: Project,
        rootTaskName: String,
        allProjects: Map<ProjectPath, Project>,
    ) {
        target.tasks.register<MigrateToBazelTask>(MigrateToBazelTask.NAME) {
            group = AIRIN_TASK_GROUP

            for ((_, dependencyProject) in allProjects) {
                val dependencyTask = dependencyProject.tasks
                    .withType<MigrateProjectToBazelTask>()
                    .firstOrNull { it.name == MigrateProjectToBazelTask.NAME }
                    ?: continue

                dependsOn(dependencyTask)
                if (dependencyTask.outputFile.isPresent) {
                    this.outputFiles.from(dependencyTask.outputFile)
                }
            }

            val rootTask = root.tasks
                .withType<MigrateRootToBazel>()
                .firstOrNull { it.name == rootTaskName }
            if (rootTask != null) {
                dependsOn(rootTask)
                if (rootTask.outputFile.isPresent) {
                    this.outputFiles.from(rootTask.outputFile)
                }
            }
        }
    }

    private fun Project.buildRootTaskName(): String {
        val suffix = path
            .split(":")
            .filter(String::isNotBlank)
            .joinToString(separator = "", transform = String::capitalized)
        return "${MigrateRootToBazel.NAME}For$suffix"
    }

    private fun registerMigrateRootToBazel(
        target: Project,
        name: String,
        projects: Map<ProjectPath, Project>,
        components: Map<ComponentId, ModuleComponent>,
        transformer: ProjectTransformer
    ) {
        target.tasks.register<MigrateRootToBazel>(name) {
            group = AIRIN_TASK_GROUP

            val (module, component) = transformer.invoke(target)

            val allModules = transformer.invoke(projects)
                .mapValues { (_, config) -> config.module }
                .filter { (_, module) -> !module.skipped }

            this.component.set(component)
            this.module.set(module)
            this.allComponents.set(components)
            this.allModules.set(allModules)
            this.outputFile.set(target.outputFile())
        }
    }

    private fun checkTargets(properties: AirinProperties) {
        require(properties.targets.isNotEmpty()) {
            "Migration targets must be configured with Airin"
        }
    }
}
