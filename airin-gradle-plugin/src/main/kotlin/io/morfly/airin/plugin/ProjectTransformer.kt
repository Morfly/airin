package io.morfly.airin.plugin

import io.morfly.airin.ComponentConflictResolution
import io.morfly.airin.ComponentId
import io.morfly.airin.ConfigurationName
import io.morfly.airin.GradleFeatureComponent
import io.morfly.airin.GradlePackageComponent
import io.morfly.airin.GradleProject
import io.morfly.airin.GradleProjectDecorator
import io.morfly.airin.MissingComponentResolution
import io.morfly.airin.dsl.AirinProperties
import io.morfly.airin.label.GradleLabel
import io.morfly.airin.label.Label
import io.morfly.airin.label.MavenCoordinates
import org.gradle.api.Project
import org.gradle.api.artifacts.ExternalDependency
import org.gradle.api.artifacts.ProjectDependency

data class ModuleConfiguration(
    val path: String,
    val project: Project,
    val module: GradleProject,
    val component: GradlePackageComponent?
)

interface ProjectTransformer {

    operator fun invoke(projects: Map<ProjectPath, Project>): Map<ProjectPath, ModuleConfiguration>

    operator fun invoke(project: Project): ModuleConfiguration
}

class DefaultProjectTransformer(
    private val components: Map<ComponentId, GradlePackageComponent>,
    private val properties: AirinProperties,
    private val decorator: GradleProjectDecorator
) : ProjectTransformer {
    private val cache = mutableMapOf<ProjectPath, ModuleConfiguration>()

    override fun invoke(projects: Map<ProjectPath, Project>): Map<ProjectPath, ModuleConfiguration> =
        projects.mapValues { (_, project) -> invoke(project) }

    override fun invoke(project: Project): ModuleConfiguration {
        cache[project.path]?.let { return it }

        val isSkipped = project.path in properties.skippedProjects

        val packageComponent =
            if (!isSkipped) project.pickPackageComponent(components, properties)
            else null
        val featureComponents =
            if (packageComponent == null) emptyList()
            else project.pickFeatureComponents(packageComponent)

        val module = GradleProject(
            name = project.name,
            isRoot = project.rootProject.path == project.path,
            label = GradleLabel(path = project.path, name = project.name),
            dirPath = project.projectDir.path,
            packageComponentId = packageComponent?.id,
            featureComponentIds = featureComponents.map { it.id }.toSet(),
            originalDependencies = project.prepareDependencies(properties)
        )
        with(decorator) {
            module.decorate(project)
        }

        val config = ModuleConfiguration(
            path = project.path,
            project = project,
            module = module,
            component = module.packageComponentId?.let(components::getValue)
        )
        cache[project.path] = config
        return config
    }

    private fun Project.pickPackageComponent(
        components: Map<ComponentId, GradlePackageComponent>,
        properties: AirinProperties
    ): GradlePackageComponent? {
        val suitableComponents = components.values
            .filter { !it.ignored }
            .filter { it.canProcess(this) }

        return when {
            suitableComponents.isEmpty() -> when (properties.onMissingComponent) {
                MissingComponentResolution.Fail -> error("No package component found for $path")
                MissingComponentResolution.Ignore -> null
            }

            suitableComponents.size > 1 -> when (properties.onComponentConflict) {
                ComponentConflictResolution.Fail -> error("Unable to pick suitable package component for $path out of ${suitableComponents.map { it.javaClass }}")
                ComponentConflictResolution.UsePriority -> suitableComponents.maxByOrNull { it.priority }
                ComponentConflictResolution.Ignore -> null
            }

            else -> suitableComponents.first()
        }
    }

    private fun Project.pickFeatureComponents(
        component: GradlePackageComponent
    ): List<GradleFeatureComponent> = component.subcomponents.values
        .filterIsInstance<GradleFeatureComponent>()
        .filter { !it.ignored }
        .filter { it.canProcess(this) }

    private fun Project.prepareDependencies(
        properties: AirinProperties
    ): Map<ConfigurationName, List<Label>> = this
        .filterConfigurations(properties)
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
}