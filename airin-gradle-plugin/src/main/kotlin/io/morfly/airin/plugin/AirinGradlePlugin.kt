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

import io.morfly.airin.Component
import io.morfly.airin.ComponentId
import io.morfly.airin.FeatureComponent
import io.morfly.airin.GradleModule
import io.morfly.airin.GradleModuleDecorator
import io.morfly.airin.ModuleComponent
import io.morfly.airin.dsl.AirinExtension
import io.morfly.airin.dsl.AirinProperties
import io.morfly.airin.extractFilePaths
import io.morfly.airin.nonAlphaNumericRegex
import io.morfly.airin.plugin.task.MigrateProjectToBazelTask
import io.morfly.airin.plugin.task.MigrateRootToBazel
import io.morfly.airin.plugin.task.MigrateToBazelTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.configurationcache.extensions.capitalized
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.withType

const val AIRIN_TASK_GROUP = "Bazel migration"

abstract class AirinGradlePlugin : Plugin<Project> {

    abstract val defaultProjectDecorator: Class<out GradleModuleDecorator>

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

            val decoratorClass = inputs.projectDecorator ?: defaultProjectDecorator
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
                        transformer = transformer,
                        properties = inputs.properties
                    )
                }

                val rootTaskName = buildRootTaskNameFor(inputProject)
                registerMigrateRootToBazelTask(
                    target = target,
                    name = rootTaskName,
                    projects = allProjects,
                    components = components,
                    transformer = transformer,
                    properties = inputs.properties
                )

                registerMigrateToBazelTask(
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

    private fun registerMigrateToBazelTask(
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
                this.outputFiles.from(dependencyTask.outputFiles)
            }

            val rootTask = root.tasks
                .withType<MigrateRootToBazel>()
                .firstOrNull { it.name == rootTaskName }
            if (rootTask != null) {
                dependsOn(rootTask)
                this.outputFiles.from(rootTask.outputFiles)
            }
        }
    }

    private fun registerMigrateProjectToBazelTask(
        target: Project,
        transformer: ProjectTransformer,
        properties: Map<String, Any>,
    ) {
        if (target.tasks.any { it.name == MigrateProjectToBazelTask.NAME }) return

        target.tasks.register<MigrateProjectToBazelTask>(MigrateProjectToBazelTask.NAME) {
            group = AIRIN_TASK_GROUP

            val (module, component) = transformer.invoke(target)
            val starlarkFiles = component?.extractFilePaths(module).orEmpty()

            this.component.set(component)
            this.componentProperties.set(component?.extractProperties())
            this.module.set(module)
            this.properties.set(properties)
            this.outputFiles.from(target.outputFiles(starlarkFiles))
        }
    }

    private fun buildRootTaskNameFor(project: Project): String {
        val suffix = project.path
            .split(nonAlphaNumericRegex)
            .filter(String::isNotBlank)
            .joinToString(separator = "", transform = String::capitalized)
        return "${MigrateRootToBazel.NAME}For$suffix"
    }

    private fun registerMigrateRootToBazelTask(
        target: Project,
        name: String,
        projects: Map<ProjectPath, Project>,
        components: Map<ComponentId, ModuleComponent>,
        transformer: ProjectTransformer,
        properties: Map<String, Any>,
    ) {
        target.tasks.register<MigrateRootToBazel>(name) {
            group = AIRIN_TASK_GROUP

            val (module, component) = transformer.invoke(target)

            val allModules = transformer.invoke(projects)
                .values
                .map { it.module }
                .filter { !it.skipped }
            val starlarkFiles = component?.extractFilePaths(module).orEmpty()

            val componentProperties = components
                .map { (_, component) -> component.extractProperties() }
                .flatMap { it.entries }
                .associate { (id, properties) -> id to properties }

            this.component.set(component)
            this.allComponentProperties.set(componentProperties)
            this.module.set(module)
            this.properties.set(properties)
            this.allComponents.set(components)
            this.allModules.set(allModules)
            this.outputFiles.from(target.outputFiles(starlarkFiles))
        }
    }

    private fun ModuleComponent.extractProperties(): Map<ComponentId, Map<String, Any>> {
        val allProperties = mutableMapOf<ComponentId, Map<String, Any>>()

        for ((id, component) in subcomponents) {
            allProperties += id to component.properties
        }
        allProperties += id to properties

        return allProperties
    }

    private fun checkTargets(properties: AirinProperties) {
        require(properties.targets.isNotEmpty()) {
            "No targets configured for Bazel migration. Please configure targets property with Airin plugin."
        }
    }
}
