package io.morfly.airin.plugin

import io.morfly.airin.GradlePackageComponent
import io.morfly.airin.GradleProject
import org.gradle.api.Project

data class ModuleConfiguration(
    val path: String,
    val project: Project,
    val module: GradleProject,
    val component: GradlePackageComponent?
)