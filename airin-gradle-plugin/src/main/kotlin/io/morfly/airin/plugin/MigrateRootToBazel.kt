package io.morfly.airin.plugin

import io.morfly.airin.ComponentId
import io.morfly.airin.GradleFeatureComponent
import io.morfly.airin.GradlePackageComponent
import io.morfly.airin.GradleProject
import io.morfly.airin.InternalAirinApi
import io.morfly.pendant.starlark.lang.context.FileContext
import io.morfly.pendant.starlark.writer.StarlarkFileWriter
import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.MapProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction

abstract class MigrateRootToBazel : DefaultTask() {

    @get:Input
    abstract val component: Property<GradlePackageComponent>

    @get:Input
    abstract val module: Property<GradleProject>

    @get:Input
    abstract val allComponents: MapProperty<ComponentId, GradlePackageComponent>

    @get:Input
    abstract val allModules: MapProperty<ProjectPath, GradleProject>

    @get:OutputFile
    // TDOO remove optional
    @get:Optional
    abstract val outputFile: RegularFileProperty

    @OptIn(InternalAirinApi::class)
    @TaskAction
    fun migrateRootToBazel() {
        val component = component.get()
        val module = module.get()

        val sharedProperties = mutableMapOf<String, Any?>()
        component.sharedProperties = sharedProperties
        for ((_, feature) in component.subcomponents) {
            feature.sharedProperties = sharedProperties
        }
        replay(sharedProperties)

        val outputs = component.invoke(module)
        writeGeneratedFiles(module.dirPath, outputs.starlarkFiles)
    }

    @OptIn(InternalAirinApi::class)
    private fun replay(sharedProperties: MutableMap<String, Any?>) {
        for ((_, module) in allModules.get()) {
            val moduleComponent = allComponents.get().getValue(module.packageComponentId!!)
            val featureComponents = moduleComponent.subcomponents.values
                .filterIsInstance<GradleFeatureComponent>()

            moduleComponent.sharedProperties = sharedProperties
            featureComponents.forEach { it.sharedProperties = sharedProperties }

            val outputs = moduleComponent.invoke(module)
            for ((_, builders) in outputs.starlarkFiles) {
                for (builder in builders) {
                    builder.build()
                }
            }
        }
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

    companion object {
        const val NAME = "migrateRootToBazel"
    }
}