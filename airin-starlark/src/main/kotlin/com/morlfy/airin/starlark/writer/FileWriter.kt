@file:Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")

package com.morlfy.airin.starlark.writer

import java.io.File


open class FileWriter : Writer<File, String> {

    override fun write(path: File, content: String) = with(path) {
        try {
            parentFile.mkdirs()
            createNewFile()
            bufferedWriter().use { out -> out.write(content) }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object Default : FileWriter()
}