package io.morfly.airin.plugin.old

import io.morfly.airin.dsl.AirinProperties
import io.morfly.airin.plugin.filterConfigurations
import org.gradle.api.Project
import org.gradle.api.artifacts.ProjectDependency

data class ProjectInfo(
    val project: Project,
    val relatedProjects: List<Project>
)

interface ProjectGraphBuilder {

    operator fun invoke(inputProjects: List<Project>): Map<String, Project>
}

class DependencyGraphBuilder(private val properties: AirinProperties) : ProjectGraphBuilder {

    override operator fun invoke(inputProjects: List<Project>): Map<String, Project> {
        val projects = mutableMapOf<String, Project>()

        fun traverse(project: Project) {
            if (project.path in projects) return
            projects[project.path] = project

            project.filterConfigurations(properties)
                .flatMap { it.dependencies }
                .filterIsInstance<ProjectDependency>()
                .map { it.dependencyProject }
                .forEach(::traverse)
        }
        inputProjects.forEach(::traverse)

        return projects
    }
}

class SubprojectGraphBuilder : ProjectGraphBuilder {

    override operator fun invoke(inputProjects: List<Project>): Map<String, Project> {
        val projects = mutableMapOf<String, Project>()

        fun traverse(project: Project) {
            if (project.path in projects) return
            projects[project.path] = project

            project.childProjects.forEach { (_, subproject) -> traverse(subproject) }
        }
        inputProjects.forEach(::traverse)
        return projects
    }
}