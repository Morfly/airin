package io.morfly.airin.plugin

import io.morfly.airin.dsl.AirinProperties
import io.morfly.pendant.starlark.lang.context.FileContext
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.file.ConfigurableFileCollection

fun Project.filterConfigurations(properties: AirinProperties): List<Configuration> =
    configurations.filter { it.filter(properties) }

fun Configuration.filter(properties: AirinProperties): Boolean {
    if (name in properties.skippedConfigurations) return false

    return properties.configurations.isEmpty() || name in properties.configurations
}

fun Project.outputFiles(starlarkFiles: Map<String, List<FileContext>>): ConfigurableFileCollection {
    val fileCollection = objects.fileCollection()
    for ((relativePath, files) in starlarkFiles) {
        for (file in files) {
            val path =
                if (relativePath.isBlank()) file.fileName
                else "$relativePath/${file.fileName}"
            fileCollection.from(layout.projectDirectory.file(path))
        }
    }
    return fileCollection
}
