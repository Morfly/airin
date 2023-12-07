/*
 * Copyright 2023 Pavlo Stavytskyi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputFiles
import java.io.File

abstract class AbstractMigrateToBazelTask : DefaultTask() {

    @get:Input
    @get:Optional
    abstract val component: Property<ModuleComponent>

    @get:Input
    abstract val module: Property<GradleModule>

    @get:OutputFiles
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