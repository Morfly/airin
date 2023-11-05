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

            val allProjects = DependencyGraphBuilder(inputs)
                .invoke(inputProjects)
            val allModules = DefaultProjectTransformer(components, inputs, decorator)
                .invoke(allProjects)

            var prev: ModuleConfiguration? = null
            for (module in allModules.asReversed()) {
                registerMigrateProjectToBazelTask(current = module, prev = prev)
                prev = module
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

    private fun registerMigrateProjectToBazelTask(
        current: ModuleConfiguration,
        prev: ModuleConfiguration?
    ) {
        val taskName = Task.migrateProjectToBazel.name

        current.project.tasks.register<MigrateToBazelTask>(taskName) {
            component.set(current.component)
            module.set(current.module)
            outputDir.set(current.project.outputDirectory())

            if (prev != null) {
                val dependency = prev.project.tasks
                    .withType<MigrateToBazelTask>()
                    .first { it.name == taskName }
                dependsOn(dependency)

                transitiveOutputDirs.from(dependency.outputDir)
                transitiveOutputDirs.from(dependency.transitiveOutputDirs)
            }
        }
    }

    private fun Project.outputDirectory(): DirectoryProperty =
        objects.directoryProperty().convention(layout.projectDirectory)
}
