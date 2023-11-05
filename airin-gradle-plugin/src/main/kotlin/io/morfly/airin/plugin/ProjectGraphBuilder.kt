package io.morfly.airin.plugin

import io.morfly.airin.dsl.AirinProperties
import org.gradle.api.Project
import org.gradle.api.artifacts.ProjectDependency

data class ProjectRelation(
    val project: Project,
    val relatedProjects: List<Project>
)

interface ProjectGraphBuilder {

    operator fun invoke(inputProjects: List<Project>): Map<ProjectPath, ProjectRelation>
}

class DependencyGraphBuilder(private val properties: AirinProperties) : ProjectGraphBuilder {

    override operator fun invoke(inputProjects: List<Project>): Map<ProjectPath, ProjectRelation> {
        val projects = mutableMapOf<ProjectPath, ProjectRelation>()

        fun traverse(project: Project) {
            if (project.path in projects) return

            val dependencies = project.filterConfigurations(properties)
                .flatMap { it.dependencies }
                .filterIsInstance<ProjectDependency>()
                .map { it.dependencyProject }
            projects[project.path] = ProjectRelation(project, dependencies)

            dependencies.forEach(::traverse)
        }
        inputProjects.forEach(::traverse)

        return projects
    }
}

class SubprojectGraphBuilder : ProjectGraphBuilder {

    override operator fun invoke(inputProjects: List<Project>): Map<ProjectPath, ProjectRelation> {
        val projects = mutableMapOf<ProjectPath, ProjectRelation>()

        fun traverse(project: Project) {
            if (project.path in projects) return

            val subprojects = project.childProjects.map { it.value }
            projects[project.path] = ProjectRelation(project, subprojects)

            subprojects.forEach(::traverse)

        }
        inputProjects.forEach(::traverse)

        return projects
    }
}