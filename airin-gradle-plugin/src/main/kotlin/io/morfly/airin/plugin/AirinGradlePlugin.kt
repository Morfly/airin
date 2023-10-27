package io.morfly.airin.plugin

import io.morfly.airin.Component
import io.morfly.airin.ComponentConflictResolution
import io.morfly.airin.GradleFeatureComponent
import io.morfly.airin.GradlePackageComponent
import io.morfly.airin.GradleProject
import io.morfly.airin.InternalAirinApi
import io.morfly.airin.MissingComponentResolution
import io.morfly.airin.dsl.AirinExtension
import io.morfly.airin.dsl.AirinProperties
import io.morfly.airin.label.GradleLabel
import io.morfly.airin.label.Label
import io.morfly.airin.label.MavenCoordinates
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.ExternalDependency
import org.gradle.api.artifacts.ProjectDependency
import org.gradle.api.file.RegularFile
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.register

abstract class AirinGradlePlugin : Plugin<Project> {

    override fun apply(target: Project) {
        val inputs = target.extensions.create<AirinExtension>(AirinExtension.NAME)

        target.tasks.register<MigrateToBazelTask>(MigrateToBazelTask.NAME) {
            val components = prepareComponents(inputs.subcomponents)
            val outputFiles = mutableListOf<RegularFile>()
            val root = prepareProjects(
                root = target,
                components = components,
                properties = inputs,
                outputFiles = outputFiles
            )

            this.components.set(components.associateBy { it.id })
            this.properties.set(inputs.properties)
            this.root.set(root)
            this.outputFiles.setFrom(outputFiles)
        }
    }

    protected open fun prepareComponents(components: List<Component<GradleProject>>): List<GradlePackageComponent> {
        val sharedFeatureComponents = components
            .filterIsInstance<GradleFeatureComponent>()
        return components
            .filterIsInstance<GradlePackageComponent>()
            .onEach { it.subcomponents += sharedFeatureComponents }
    }

    protected open fun prepareProjects(
        root: Project,
        components: List<GradlePackageComponent>,
        properties: AirinProperties,
        outputFiles: MutableList<RegularFile>
    ): GradleProject {

        fun traverse(target: Project): GradleProject {
            val packageComponent = target.pickPackageComponent(components, properties)
            val featureComponents =
                if (packageComponent == null) emptyList()
                else target.pickFeatureComponents(packageComponent)

            val project = GradleProject(
                name = target.name,
                isRoot = target.rootProject.path == target.path,
                label = GradleLabel(projectPath = target.path),
                dirPath = target.projectDir.path,
                ignored = packageComponent == null,
                packageComponentId = packageComponent?.id,
                featureComponentIds = featureComponents.map { it.id }.toSet()
            )
            project.originalDependencies =
                if (!project.ignored) prepareDependencies(target)
                else emptyMap()
            project.subpackages = target.childProjects.values.map(::traverse)

            if (!project.ignored && packageComponent != null) {
                outputFiles += project
                    .collectFilePaths(packageComponent)
                    .map(target.layout.projectDirectory::file)
            }
            return project
        }
        return traverse(root)
    }

    // TODO filter configurations
    protected open fun prepareDependencies(target: Project): Map<String, List<Label>> =
        target.configurations
            .associateBy({ it.name }, { it.dependencies })
            .filter { (_, dependencies) -> dependencies.isNotEmpty() }
            .mapValues { (_, dependencies) ->
                dependencies.mapNotNull {
                    when (it) {
                        is ExternalDependency -> MavenCoordinates(it.group, it.name, it.version)
                        is ProjectDependency -> GradleLabel(it.dependencyProject.path)
                        else -> null
                    }
                }
            }

    protected open fun Project.pickPackageComponent(
        components: List<GradlePackageComponent>,
        properties: AirinProperties
    ): GradlePackageComponent? {
        val suitableComponents = components
            .filter { !it.ignored }
            .filter { it.canProcess(this) }

        return when {
            suitableComponents.isEmpty() -> when (properties.onMissingComponent) {
                MissingComponentResolution.Fail -> error("No package component found for $name")
                MissingComponentResolution.Ignore -> null
            }

            suitableComponents.size > 1 -> when (properties.onComponentConflict) {
                ComponentConflictResolution.Fail -> error("Unable to pick suitable package component for $name out of ${suitableComponents.map { it.javaClass }}")
                ComponentConflictResolution.UsePriority -> suitableComponents.maxByOrNull { it.priority }
                ComponentConflictResolution.Ignore -> null
            }

            else -> suitableComponents.first()
        }
    }

    protected open fun Project.pickFeatureComponents(
        component: GradlePackageComponent
    ): List<GradleFeatureComponent> = component.subcomponents
        .filterIsInstance<GradleFeatureComponent>()
        .filter { !it.ignored }
        .filter { it.canProcess(this) }

    @OptIn(InternalAirinApi::class)
    protected open fun GradleProject.collectFilePaths(
        component: GradlePackageComponent
    ): List<String> {
        val result = component.invoke(this, includeSubcomponents = false)

        return result.starlarkFiles.flatMap { (path, files) ->
            files.map { file ->
                if (path.isEmpty()) file.fileName
                else "$path/${file.fileName}"
            }
        }
    }
}
