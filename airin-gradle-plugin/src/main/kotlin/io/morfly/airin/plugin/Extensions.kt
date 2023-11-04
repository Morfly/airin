package io.morfly.airin.plugin

import io.morfly.airin.dsl.AirinProperties
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration

fun Project.filterConfigurations(properties: AirinProperties): List<Configuration> =
    configurations.filter { it.filter(properties) }

fun Configuration.filter(properties: AirinProperties): Boolean {
    if (name in properties.skippedConfigurations) return false

    return properties.configurations.isEmpty() || name in properties.configurations
}

