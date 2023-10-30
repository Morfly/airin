package io.morfly.airin.plugin

import io.morfly.airin.Component
import io.morfly.airin.ComponentConflictResolution
import io.morfly.airin.ComponentId
import io.morfly.airin.ConfigurationName
import io.morfly.airin.GradleFeatureComponent
import io.morfly.airin.GradlePackageComponent
import io.morfly.airin.GradleProject
import io.morfly.airin.GradleProjectDecorator
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

    abstract val defaultProjectDecorator: Class<out GradleProjectDecorator>

    override fun apply(target: Project) {
        require(target.rootProject.path == target.path) {
            "Airin must be applied to the root project but was applied to ${target.path}!"
        }

        val inputs = target.extensions.create<AirinExtension>(AirinExtension.NAME)

        target.tasks.register<MigrateToBazelTask>(MigrateToBazelTask.NAME) {
            val decoratorClass =
                if (inputs.projectDecorator != GradleProjectDecorator::class.java) inputs.projectDecorator
                else defaultProjectDecorator

            val components = prepareComponents(inputs.subcomponents)
            val outputFiles = mutableListOf<RegularFile>()
            val root = prepareProjects(
                root = target,
                components = components,
                properties = inputs,
                decorator = inputs.objects.newInstance(decoratorClass),
                outputFiles = outputFiles
            )

            this.components.set(components)
            this.properties.set(inputs.properties)
            this.root.set(root)
            this.outputFiles.setFrom(outputFiles)
        }
    }

    protected open fun prepareComponents(
        components: Map<ComponentId, Component<GradleProject>>
    ): Map<ComponentId, GradlePackageComponent> {
        // Feature components shared with every package component
        val completelySharedFeatureComponents = components.values.asSequence()
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

        return components.values.asSequence()
            .filterIsInstance<GradlePackageComponent>()
            .onEach { it.subcomponents += completelySharedFeatureComponents }
            .onEach { if (it.shared) it.subcomponents += sharedFeatureComponents }
            .associateBy { it.id }
    }

    protected open fun prepareProjects(
        root: Project,
        components: Map<ComponentId, GradlePackageComponent>,
        properties: AirinProperties,
        decorator: GradleProjectDecorator,
        outputFiles: MutableList<RegularFile>,
    ): GradleProject {
        val allowedTargets = mutableSetOf<String>()

        fun traverse(target: Project): GradleProject {
            val packageComponent = target.pickPackageComponent(components, properties)
            val featureComponents =
                if (packageComponent == null) emptyList()
                else target.pickFeatureComponents(packageComponent)

            val project = GradleProject(
                name = target.name,
                isRoot = target.rootProject.path == target.path,
                label = GradleLabel(path = target.path, name = target.name),
                dirPath = target.projectDir.path,
            ).apply {
                if (label.path in properties.allowedTargets || properties.allowedTargets.isEmpty())
                    allowedTargets += label.path

                if (label.path in allowedTargets || isRoot) {
                    packageComponentId = packageComponent?.id
                    featureComponentIds = featureComponents.map { it.id }.toSet()
                } else {
                    packageComponentId = null
                    featureComponentIds = emptySet()
                }
                originalDependencies =
                    if (!ignored) prepareDependencies(target, properties)
                    else emptyMap()

                if (label.path in allowedTargets) {
                    originalDependencies.values.flatten()
                        .filterIsInstance<GradleLabel>()
                        .forEach { allowedTargets += it.path }
                }
                subpackages = target.childProjects.values.map(::traverse)
            }
            with(decorator) {
                project.decorate(target)
            }

            if (!project.ignored && packageComponent != null) {
                outputFiles += project
                    .collectFilePaths(packageComponent)
                    .map(target.layout.projectDirectory::file)
            }
            return project
        }
        return traverse(root)
    }

    protected open fun prepareDependencies(
        target: Project,
        properties: AirinProperties
    ): Map<String, List<Label>> =
        target.configurations
            .filter { filterConfiguration(it.name, properties) }
            .associateBy({ it.name }, { it.dependencies })
            .filter { (_, dependencies) -> dependencies.isNotEmpty() }
            .mapValues { (_, dependencies) ->
                dependencies.mapNotNull {
                    when (it) {
                        is ExternalDependency -> MavenCoordinates(it.group!!, it.name, it.version)
                        is ProjectDependency -> with(it.dependencyProject) {
                            GradleLabel(path = path, name = name)
                        }

                        else -> null
                    }
                }
            }

    protected open fun filterConfiguration(
        configuration: ConfigurationName, properties: AirinProperties
    ): Boolean {
        if (configuration in properties.ignoredConfigurations) return false

        return with(properties.allowedConfigurations) {
            isEmpty() || any { configuration.contains(it, ignoreCase = true) }
        }
    }

    protected open fun Project.pickPackageComponent(
        components: Map<ComponentId, GradlePackageComponent>,
        properties: AirinProperties
    ): GradlePackageComponent? {
        val suitableComponents = components.values
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
    ): List<GradleFeatureComponent> = component.subcomponents.values
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
