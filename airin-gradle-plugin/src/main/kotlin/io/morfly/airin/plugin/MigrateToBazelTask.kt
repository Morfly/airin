package io.morfly.airin.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputDirectories
import org.gradle.api.tasks.TaskAction

abstract class MigrateToBazelTask : DefaultTask() {

    @get:OutputDirectories
    @get:Optional
    abstract val outputDirs: ConfigurableFileCollection

    @TaskAction
    fun migrateToBazel() {
        println("TTAGG ${outputDirs.map { it.path }}")
    }
}