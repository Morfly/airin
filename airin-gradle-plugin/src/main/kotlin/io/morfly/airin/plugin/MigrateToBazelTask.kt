package io.morfly.airin.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.tasks.OutputFiles
import org.gradle.api.tasks.TaskAction

abstract class MigrateToBazelTask : DefaultTask() {

    @get:OutputFiles
    abstract val outputFiles: ConfigurableFileCollection

    @TaskAction
    fun migrateToBazel() = Unit

    companion object {
        const val NAME = "migrateToBazel"
    }
}