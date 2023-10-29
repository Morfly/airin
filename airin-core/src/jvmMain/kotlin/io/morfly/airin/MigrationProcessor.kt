package io.morfly.airin

import io.morfly.pendant.starlark.lang.context.FileContext
import io.morfly.pendant.starlark.writer.StarlarkFileWriter

class MigrationProcessor<P : PackageDescriptor>(
    private val components: Map<String, PackageComponent<P>>,
    // TODO check generated files with outputs
) {

    @InternalAirinApi
    fun invoke(packageDescriptor: P) {

        val packages = mutableMapOf<ComponentId, MutableList<P>>()

        fun traverse(pkg: P) {
            val id = pkg.packageComponentId
            if (id != null && !pkg.ignored) {
                packages.getOrPut(id, ::mutableListOf) += pkg
            }
            for (subpackage in pkg.subpackages) {
                @Suppress("UNCHECKED_CAST")
                traverse(subpackage as P)
            }
        }
        traverse(packageDescriptor)

        // Processing packages, following the order in which components were registered.
        val sharedProperties = mutableMapOf<String, Any?>()
        for ((id, component) in components) {
            component.sharedProperties = sharedProperties

            for (pkg in packages[id].orEmpty()) {
                val result = component.invoke(pkg)
                writeGeneratedFiles(pkg.dirPath, result.starlarkFiles)
            }
        }
    }

    fun extractOutputFilePaths() {
        TODO()
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

}
