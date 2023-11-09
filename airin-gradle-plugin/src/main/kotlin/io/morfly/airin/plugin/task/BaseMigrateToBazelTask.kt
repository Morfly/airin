package io.morfly.airin.plugin.task

import io.morfly.airin.GradleModule
import io.morfly.airin.InternalAirinApi
import io.morfly.airin.ModuleComponent
import io.morfly.pendant.starlark.element.StarlarkFile
import io.morfly.pendant.starlark.lang.context.FileContext
import io.morfly.pendant.starlark.writer.StarlarkFileWriter
import org.gradle.api.DefaultTask
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.provider.Property
import java.io.File

abstract class BaseMigrateToBazelTask : DefaultTask() {

    abstract val component: Property<ModuleComponent>

    abstract val module: Property<GradleModule>

    abstract val outputFiles: ConfigurableFileCollection

    @OptIn(InternalAirinApi::class)
    protected fun processAndWrite(sharedProperties: MutableMap<String, Any>) {
        val outputs = component.get().invoke(module.get(), sharedProperties)
        writeGeneratedFiles(outputs.starlarkFiles)
    }

    private fun writeGeneratedFiles(files: Map<String, List<FileContext>>) {
        val module = module.get()
        val outputFiles = outputFiles.files

        for ((relativeDirPath, builders) in files) {
            for (builder in builders) {
                val path = "${module.dirPath}/$relativeDirPath"
                val file = builder.build()
                outputFiles.validateFilePath(path, file)
                StarlarkFileWriter.write(path, file)
            }
        }
    }

    private fun Set<File>.validateFilePath(path: String, file: StarlarkFile) {
        val filePath = "$path/${file.name}"
        require(File(filePath) in this) { "Invalid file path: $filePath" }
    }
}