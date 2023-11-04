package io.morfly.airin.plugin

import io.morfly.airin.GradlePackageComponent
import io.morfly.airin.GradleProject
import org.gradle.api.DefaultTask
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputDirectories
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction

abstract class MigrateToBazelTask : DefaultTask() {

    @get:Input
    @get:Optional
    abstract val component: Property<GradlePackageComponent>

    @get:Input
    abstract val module: Property<GradleProject>

    @get:OutputDirectory
    abstract val outputDir: DirectoryProperty

    @get:OutputDirectories
    @get:Optional
    abstract val transitiveOutputDirs: ConfigurableFileCollection

    @TaskAction
    fun migrateToBazel() {
        println("TTAGG migrateToBazel: ${module.get().label.path}")
        println("TTAGG output: ${outputDir.get().asFile.path}")
        println("TTAGG transitive: ${transitiveOutputDirs.map { it.path }}")
    }

    companion object {
        const val NAME = "migrateToBazel"
    }
}
