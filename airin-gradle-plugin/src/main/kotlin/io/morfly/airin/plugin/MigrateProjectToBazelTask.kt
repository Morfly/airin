package io.morfly.airin.plugin

import io.morfly.airin.GradlePackageComponent
import io.morfly.airin.GradleProject
import io.morfly.airin.InternalAirinApi
import io.morfly.pendant.starlark.lang.context.FileContext
import io.morfly.pendant.starlark.writer.StarlarkFileWriter
import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction

abstract class MigrateProjectToBazelTask : DefaultTask() {

    @get:Input
    @get:Optional
    abstract val component: Property<GradlePackageComponent>

    @get:Input
    abstract val module: Property<GradleProject>

    @get:OutputFile
    abstract val outputFile: RegularFileProperty

    @OptIn(InternalAirinApi::class)
    @TaskAction
    fun migrateProjectToBazel() {
        if (!component.isPresent) return

        val component = component.get()
        val module = module.get()

        val sharedProperties = mutableMapOf<String, Any?>()
        component.sharedProperties = sharedProperties
        for ((_, feature) in component.subcomponents) {
            feature.sharedProperties = sharedProperties
        }

        val outputs = component.invoke(module)
        writeGeneratedFiles(module.dirPath, outputs.starlarkFiles)
    }

    private fun writeGeneratedFiles(dirPath: String, files: Map<String, List<FileContext>>) {
        for ((relativeDirPath, builders) in files) {
            for (builder in builders) {
                val path = "$dirPath/$relativeDirPath"
                val file = builder.build()
                StarlarkFileWriter.write(path, file)
            }
        }
    }

    companion object {
        const val NAME = "migrateProjectToBazel"
    }
}
