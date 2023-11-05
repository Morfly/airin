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
import org.gradle.api.file.DirectoryProperty
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.withType

abstract class AirinGradlePlugin : Plugin<Project> {

    enum class Task {
        migrateToBazel,
        migrateProjectToBazel,
        migrateRootToBazel,
    }

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

            val graphBuilder = DependencyGraphBuilder(inputs)
            val transformer = DefaultProjectTransformer(components, inputs, decorator)
            for (inputProject in inputProjects) {
                val allProjects = graphBuilder.invoke(inputProject)
                val allModules = transformer.invoke(allProjects)

                for ((_, module) in allModules) {
                    registerMigrateProjectToBazelTask(module)
                }
                registerMigrateToBazel(
                    module = allModules[inputProject.path]!!,
                    allProjects = allProjects
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

    private fun registerMigrateProjectToBazelTask(module: ModuleConfiguration) {
        if (module.project.tasks.any { it.name == Task.migrateProjectToBazel.name }) return

        module.project.tasks.register<MigrateProjectToBazelTask>(Task.migrateProjectToBazel.name) {
            this.component.set(module.component)
            this.module.set(module.module)
            this.outputDir.set(module.project.outputDirectory())
        }
    }

    private fun registerMigrateToBazel(
        module: ModuleConfiguration,
        allProjects: Map<ProjectPath, ProjectRelation>
    ) {
        module.project.tasks.register<MigrateToBazelTask>(Task.migrateToBazel.name) {
            for ((_, relatedProjects) in allProjects) {
                val dependency = relatedProjects.project.tasks
                    .withType<MigrateProjectToBazelTask>()
                    .first { it.name == Task.migrateProjectToBazel.name }

                dependsOn(dependency)
                this.outputDirs.from(dependency.outputDir)
            }
        }
    }

    private fun Project.outputDirectory(): DirectoryProperty =
        objects.directoryProperty().convention(layout.projectDirectory)
}
