/*
 * Copyright 2021 Pavlo Stavytskyi
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

@file:Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")

package org.morfly.airin.starlark.writer

import org.morfly.airin.starlark.elements.StarlarkFile
import org.morfly.airin.starlark.format.StarlarkFileFormatter
import java.io.File


/**
 * Converts a Starlark file syntax tree to the compilable Starlark file and writes it to the given path.
 */
open class StarlarkFileWriter private constructor(
    private val formatter: StarlarkFileFormatter = StarlarkFileFormatter,
    private val writer: FileWriter = FileWriter
) : Writer<File, StarlarkFile, Unit> {

    override fun write(projectRootDir: File, content: StarlarkFile) = with(content) {
        val relPath = if (relativePath.isNotEmpty()) "/$relativePath" else ""
        val fullPath = File("${projectRootDir.path}$relPath/$name")
        writer.write(fullPath, formatter.format(content))
    }

    /**
     * Default instance of a starlark file writer.
     */
    companion object Default : StarlarkFileWriter()
}