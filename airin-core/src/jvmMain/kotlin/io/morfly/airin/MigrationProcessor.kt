package io.morfly.airin

import io.morfly.pendant.starlark.writer.StarlarkFileWriter

class MigrationProcessor<P : PackageDescriptor>(
    private val components: Map<String, PackageComponent<P>>,
) {

    @InternalAirinApi
    fun invoke(packageDescriptor: P) {

        for (subpackage in packageDescriptor.subpackages) {
            @Suppress("UNCHECKED_CAST")
            invoke(subpackage as P)
        }

        if (packageDescriptor.ignored || packageDescriptor.packageComponentId == null) return

        val component = components[packageDescriptor.packageComponentId]!!
        val result = component.invoke(packageDescriptor)

        for ((relativeDirPath, builders) in result.starlarkFiles) {
            for (builder in builders) {
                val path = "${packageDescriptor.dirPath}/$relativeDirPath"
                val file = builder.build()
                StarlarkFileWriter.write(path, file)
            }
        }
    }
}
