/*
 * Copyright 2023 Pavlo Stavytskyi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.morfly.airin.plugin

import io.morfly.airin.ComponentConflictResolution
import io.morfly.airin.ComponentId
import io.morfly.airin.ConfigurationName
import io.morfly.airin.FeatureComponent
import io.morfly.airin.ModuleComponent
import io.morfly.airin.GradleModule
import io.morfly.airin.GradleModuleDecorator
import io.morfly.airin.MissingComponentResolution
import io.morfly.airin.dsl.AirinProperties
import io.morfly.airin.label.GradleLabel
import io.morfly.airin.label.Label
import io.morfly.airin.label.MavenCoordinates
import org.gradle.api.Project
import org.gradle.api.artifacts.ExternalDependency
import org.gradle.api.artifacts.ProjectDependency

data class ModuleConfiguration(
    val module: GradleModule,
    val component: ModuleComponent?
)

interface ProjectTransformer {

    fun invoke(project: Project): ModuleConfiguration
}

class DefaultProjectTransformer(
    private val components: Map<ComponentId, ModuleComponent>,
    private val properties: AirinProperties,
    private val decorator: GradleModuleDecorator,
    private val artifactCollector: ArtifactDependencyCollector
) : ProjectTransformer {
    private val cache = mutableMapOf<ProjectPath, ModuleConfiguration>()

    override fun invoke(project: Project): ModuleConfiguration {
        cache[project.path]?.let { return it }

        val isSkipped = project.path in properties.skippedProjects

        val packageComponent =
            if (!isSkipped) project.pickPackageComponent(components, properties)
            else null
        val featureComponents =
            if (packageComponent == null) emptyList()
            else project.pickFeatureComponents(packageComponent)

        val module = GradleModule(
            name = project.name,
            isRoot = project.rootProject.path == project.path,
            label = GradleLabel(path = project.path, name = project.name),
            dirPath = project.projectDir.path,
            relativeDirPath = project.projectDir.relativeTo(project.rootDir).path,
            moduleComponentId = packageComponent?.id,
            featureComponentIds = featureComponents.map { it.id }.toSet(),
            originalDependencies = project.prepareDependencies()
        )
        with(decorator) {
            module.decorate(project)
        }

        val config = ModuleConfiguration(
            module = module,
            component = module.moduleComponentId?.let(components::getValue)
        )
        cache[project.path] = config
        return config
    }

    private fun Project.pickPackageComponent(
        components: Map<ComponentId, ModuleComponent>,
        properties: AirinProperties
    ): ModuleComponent? {
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
        component: ModuleComponent
    ): List<FeatureComponent> = component.subcomponents.values
        .filterIsInstance<FeatureComponent>()
        .filter { !it.ignored }
        .filter { it.canProcess(this) }

    private fun Project.prepareDependencies(): Map<ConfigurationName, List<Label>> =
        artifactCollector
            .invoke(this)
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

fun ProjectTransformer.invoke(projects: Map<ProjectPath, Project>): Map<ProjectPath, ModuleConfiguration> =
    projects.mapValues { (_, project) -> invoke(project) }
