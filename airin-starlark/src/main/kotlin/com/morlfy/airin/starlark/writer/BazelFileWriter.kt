@file:Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")

package com.morlfy.airin.starlark.writer

import com.morlfy.airin.starlark.elements.BazelFile
import com.morlfy.airin.starlark.format.BazelFileFormatter
import java.io.File


open class BazelFileWriter private constructor(
    private val formatter: BazelFileFormatter = BazelFileFormatter,
    private val writer: FileWriter = FileWriter()
) : Writer<File, BazelFile> {

    override fun write(projectRootDir: File, content: BazelFile) = with(content) {
        val path = File("${projectRootDir.path}/$relativePath/$name")
        writer.write(path, formatter.format(content))
    }

    companion object Default : BazelFileWriter()
}