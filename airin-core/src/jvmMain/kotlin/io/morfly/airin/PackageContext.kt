package io.morfly.airin

import io.morfly.pendant.starlark.lang.context.FileContext

// TODO rename
class PackageContext {

    val starlarkFiles = mutableMapOf<String, MutableList<FileContext>>()

    fun generate(vararg files: FileContext, relativeDirPath: String = "") {
        for (file in files) {
            starlarkFiles.getOrPut(relativeDirPath.fixPath(), ::mutableListOf) += file
        }
    }
}
