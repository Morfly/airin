package io.morfly.airin.plugin

import io.morfly.airin.GradlePackageComponent
import io.morfly.airin.GradleProject
import io.morfly.airin.InternalAirinApi
import io.morfly.airin.MigrationProcessor
import org.gradle.api.DefaultTask
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.provider.MapProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputFiles
import org.gradle.api.tasks.TaskAction

abstract class MigrateToBazelTask : DefaultTask() {

    @get:Input
    abstract val components: MapProperty<String, GradlePackageComponent>

    @get:Input
    abstract val properties: MapProperty<String, Any?>

    @get:Input
    abstract val root: Property<GradleProject>

    @get:OutputFiles
    abstract val outputFiles: ConfigurableFileCollection

    @TaskAction
    @OptIn(InternalAirinApi::class)
    fun migrateToBazel() {
        val processor = MigrationProcessor(components = components.get())

        processor.invoke(root.get())
    }

    companion object {
        const val NAME = "migrateToBazel"
    }
}
