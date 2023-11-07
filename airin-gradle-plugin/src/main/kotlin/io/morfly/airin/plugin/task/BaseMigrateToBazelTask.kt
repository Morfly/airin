package io.morfly.airin.plugin.task

import io.morfly.airin.GradlePackageComponent
import io.morfly.airin.GradleProject
import io.morfly.airin.InternalAirinApi
import io.morfly.pendant.starlark.lang.context.FileContext
import io.morfly.pendant.starlark.writer.StarlarkFileWriter
import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property

abstract class BaseMigrateToBazelTask : DefaultTask() {

    abstract val component: Property<GradlePackageComponent>

    abstract val module: Property<GradleProject>

    abstract val outputFile: RegularFileProperty

    @OptIn(InternalAirinApi::class)
    protected fun setupSharedProperties(
        component: GradlePackageComponent,
        sharedProperties: MutableMap<String, Any?>
    ) {
        component.sharedProperties = sharedProperties
        for ((_, feature) in component.subcomponents) {
            feature.sharedProperties = sharedProperties
        }
    }

    @OptIn(InternalAirinApi::class)
    protected fun processAndWrite() {
        val outputs = component.get().invoke(module.get())
        val generatedFiles = writeGeneratedFiles(outputs.starlarkFiles)
        writeTaskOutputs(generatedFiles)
    }

    private fun writeGeneratedFiles(files: Map<String, List<FileContext>>): List<String> {
        val module = module.get()
        val relativeFilePaths = mutableListOf<String>()

        for ((relativeDirPath, builders) in files) {
            for (builder in builders) {
                val path = "${module.dirPath}/$relativeDirPath"
                val file = builder.build()
                StarlarkFileWriter.write(path, file)

                relativeFilePaths +=
                    if (relativeDirPath.isBlank()) file.name
                    else "$relativeDirPath/${file.name}"
            }
        }
        return relativeFilePaths
    }

    private fun writeTaskOutputs(files: List<String>) {
        val generatedFiles = files
            .joinToString(separator = "\n") { "${module.get().relativeDirPath}/$it" }

        outputFile.get().asFile.writeText(generatedFiles)
    }
}