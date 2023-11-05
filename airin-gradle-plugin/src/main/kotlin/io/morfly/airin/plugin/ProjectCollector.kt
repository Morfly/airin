package io.morfly.airin.plugin

import io.morfly.airin.dsl.AirinProperties
import org.gradle.api.Project
import org.gradle.api.artifacts.ProjectDependency

interface ProjectCollector {

    operator fun invoke(inputProject: Project): Map<ProjectPath, Project>
}

class DependencyCollector(private val properties: AirinProperties) : ProjectCollector {
    private val cache = mutableMapOf<ProjectPath, Project>()

    override operator fun invoke(inputProject: Project): Map<ProjectPath, Project> {
        val projects = mutableMapOf<ProjectPath, Project>()

        fun traverse(project: Project) {
            if (project.path in projects) return
            if (project.path in cache) {
                projects[project.path] = cache[project.path]!!
                return
            }

            val dependencies = project
                .filterConfigurations(properties)
                .flatMap { it.dependencies }
                .filterIsInstance<ProjectDependency>()
                .map { it.dependencyProject }
            projects[project.path] = project

            dependencies.forEach(::traverse)
        }
        traverse(inputProject)

        cache += projects
        return projects
    }
}

class SubprojectCollector : ProjectCollector {

    override operator fun invoke(inputProject: Project): Map<ProjectPath, Project> {
        val projects = mutableMapOf<ProjectPath, Project>()

        fun traverse(project: Project) {
            if (project.path in projects) return

            val subprojects = project.childProjects.map { it.value }
            projects[project.path] = project

            subprojects.forEach(::traverse)

        }
        traverse(inputProject)

        return projects
    }
}
