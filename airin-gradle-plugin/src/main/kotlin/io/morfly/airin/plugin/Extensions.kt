package io.morfly.airin.plugin

import io.morfly.airin.dsl.AirinProperties
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.file.DirectoryProperty

fun Project.filterConfigurations(properties: AirinProperties): List<Configuration> =
    configurations.filter { it.filter(properties) }

fun Configuration.filter(properties: AirinProperties): Boolean {
    if (name in properties.skippedConfigurations) return false

    return properties.configurations.isEmpty() || name in properties.configurations
}

fun Project.outputDirectory(): DirectoryProperty =
    objects.directoryProperty().convention(layout.projectDirectory)
