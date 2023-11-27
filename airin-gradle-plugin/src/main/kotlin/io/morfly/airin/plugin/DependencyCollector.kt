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

import io.morfly.airin.ConfigurationName
import io.morfly.airin.dsl.AirinProperties
import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.ProjectDependency

interface DependencyCollector<D> {

    operator fun invoke(inputProject: Project): Map<String, D>
}

class ProjectDependencyCollector(
    private val properties: AirinProperties,
    private val transitive: Boolean = true,
) : DependencyCollector<Project> {
    private val cache = mutableMapOf<ProjectPath, Project>()

    override operator fun invoke(inputProject: Project): Map<ProjectPath, Project> {
        val projects = mutableMapOf<ProjectPath, Project>()

        fun traverse(project: Project, transitive: Boolean) {
            if (project.path in projects) return
            if (project.path in cache) {
                projects[project.path] = cache[project.path]!!
                return
            }
            projects[project.path] = project
            if (!transitive) return

            val dependencies = project
                .filterConfigurations(properties)
                .flatMap { it.dependencies }
                .filterIsInstance<ProjectDependency>()
                .map { it.dependencyProject }

            dependencies.forEach { traverse(it, this.transitive) }
        }
        traverse(inputProject, transitive = true)

        cache += projects
        return projects
    }
}

class ArtifactDependencyCollector(
    private val properties: AirinProperties
) : DependencyCollector<Set<Dependency>> {
    private val cache = mutableMapOf<ProjectPath, Map<ConfigurationName, Set<Dependency>>>()

    override fun invoke(inputProject: Project): Map<ConfigurationName, Set<Dependency>> {
        // TODO avoid force unwrap
        if (inputProject.path in cache) {
            return cache[inputProject.path]!!
        }

        val dependencies = inputProject.filterConfigurations(properties)
            .associateBy({ it.name }, { it.dependencies })
            .filter { (_, dependencies) -> dependencies.isNotEmpty() }

        cache[inputProject.path] = dependencies
        return dependencies
    }
}
