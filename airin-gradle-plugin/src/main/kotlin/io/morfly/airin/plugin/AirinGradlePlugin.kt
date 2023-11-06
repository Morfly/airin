package io.morfly.airin.plugin

import io.morfly.airin.Component
import io.morfly.airin.ComponentId
import io.morfly.airin.GradleFeatureComponent
import io.morfly.airin.GradlePackageComponent
import io.morfly.airin.GradleProject
import io.morfly.airin.GradleProjectDecorator
import io.morfly.airin.dsl.AirinExtension
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
            if (inputs.inputProjects.isEmpty()) error("TODO1")

            val inputProjects = inputs.inputProjects.map(target::project)
            val components = prepareComponents(inputs.subcomponents)
            // TODO replace with null check instead of default
            val decoratorClass =
                if (inputs.projectDecorator != GradleProjectDecorator::class.java) inputs.projectDecorator
                else defaultProjectDecorator
            val decorator = inputs.objects.newInstance(decoratorClass)

            val collector = DependencyCollector(inputs)
            val transformer = DefaultProjectTransformer(components, inputs, decorator)
            for (inputProject in inputProjects) {
                val allProjects = collector.invoke(inputProject)
                val allModules = transformer.invoke(allProjects)

                for ((_, project, module, component) in allModules.values) {
                    if (module.skipped || component == null) continue
                    project.registerMigrateProjectToBazelTask(
                        module = module,
                        component = component
                    )
                }

                val root = transformer.invoke(target)
                val rootTaskName = inputProject.buildRootTaskName()
                if (!root.module.skipped && root.component != null) {
                    target.registerMigrateRootToBazel(
                        name = rootTaskName,
                        component = root.component,
                        module = root.module,
                        allComponents = components,
                        allModules = allModules.mapValues { (_, config) -> config.module }
                    )
                }
                inputProject.registerMigrateToBazel(
                    root = target,
                    rootTaskName = rootTaskName,
                    allProjects = allProjects,
                )
            }
        }
    }

    private fun prepareComponents(
        components: Map<ComponentId, Component<GradleProject>>
    ): Map<ComponentId, GradlePackageComponent> {
        // Feature components shared with every package component
        val topLevelSharedFeatureComponents = components.values.asSequence()
            .filterIsInstance<GradleFeatureComponent>()
            .onEach { it.shared = true }
            .associateBy { it.id }

        // Feature components shared only with shared package components
        val sharedFeatureComponents = components.values.asSequence()
            .filterIsInstance<GradlePackageComponent>()
            .flatMap { it.subcomponents.values }
            .filterIsInstance<GradleFeatureComponent>()
            .filter { it.shared }
            .associateBy { it.id }

        // Package components with added feature components
        return components.values.asSequence()
            .filterIsInstance<GradlePackageComponent>()
            .onEach { it.subcomponents += topLevelSharedFeatureComponents }
            .onEach { if (it.shared) it.subcomponents += sharedFeatureComponents }
            .associateBy { it.id }
    }

    private fun Project.registerMigrateProjectToBazelTask(
        module: GradleProject,
        component: GradlePackageComponent
    ) {
        if (tasks.any { it.name == MigrateProjectToBazelTask.NAME }) return

        tasks.register<MigrateProjectToBazelTask>(MigrateProjectToBazelTask.NAME) {
            group = AIRIN_TASK_GROUP
            this.component.set(component)
            this.module.set(module)
            this.outputDir.set(outputDirectory())
        }
    }

    private fun Project.registerMigrateToBazel(
        root: Project,
        rootTaskName: String,
        allProjects: Map<ProjectPath, Project>,
    ) {
        tasks.register<MigrateToBazelTask>(MigrateToBazelTask.NAME) {
            group = AIRIN_TASK_GROUP
            for ((_, project) in allProjects) {
                val dependency = project.tasks
                    .withType<MigrateProjectToBazelTask>()
                    .firstOrNull { it.name == MigrateProjectToBazelTask.NAME }
                    ?: continue

                dependsOn(dependency)
                if (dependency.outputDir.isPresent) {
                    this.outputDirs.from(dependency.outputDir)
                }
            }

            val rootTask = root.tasks
                .withType<MigrateRootToBazel>()
                .firstOrNull { it.name == rootTaskName }
            if (rootTask != null) {
                dependsOn(rootTask)
                if (rootTask.outputDir.isPresent) {
                    this.outputDirs.from(rootTask.outputDir)
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

    private fun Project.registerMigrateRootToBazel(
        name: String,
        component: GradlePackageComponent,
        module: GradleProject,
        allComponents: Map<ComponentId, GradlePackageComponent>,
        allModules: Map<ProjectPath, GradleProject>
    ) {
        tasks.register<MigrateRootToBazel>(name) {
            group = AIRIN_TASK_GROUP
            val filteredModules = allModules
                .filter { (_, module) -> !module.skipped }
                .filter { (path, _) -> path != this.path }
            this.component.set(component)
            this.module.set(module)
            this.allComponents.set(allComponents)
            this.allModules.set(filteredModules)
        }
    }
}
