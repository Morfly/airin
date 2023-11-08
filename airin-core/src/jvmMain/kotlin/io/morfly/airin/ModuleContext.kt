package io.morfly.airin

import io.morfly.pendant.starlark.lang.context.FileContext

class ModuleContext(
    override val sharedProperties: MutableMap<String, Any?> = mutableMapOf()
) : SharedPropertiesHolder {

    val starlarkFiles = mutableMapOf<String, MutableList<FileContext>>()

    fun generate(vararg files: FileContext, relativeDirPath: String = "") {
        for (file in files) {
            starlarkFiles.getOrPut(relativeDirPath.fixPath(), ::mutableListOf) += file
        }
    }
}
