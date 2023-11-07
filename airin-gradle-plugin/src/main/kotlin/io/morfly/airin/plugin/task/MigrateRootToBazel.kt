package io.morfly.airin.plugin.task

import io.morfly.airin.ComponentId
import io.morfly.airin.ModuleComponent
import io.morfly.airin.GradleModule
import io.morfly.airin.InternalAirinApi
import io.morfly.airin.plugin.ProjectPath
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.MapProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction

abstract class MigrateRootToBazel : BaseMigrateToBazelTask() {

    @get:Input
    @get:Optional
    abstract override val component: Property<ModuleComponent>

    @get:Input
    abstract override val module: Property<GradleModule>

    @get:Input
    abstract val allComponents: MapProperty<ComponentId, ModuleComponent>

    @get:Input
    abstract val allModules: MapProperty<ProjectPath, GradleModule>

    @get:OutputFile
    abstract override val outputFile: RegularFileProperty

    @TaskAction
    fun migrateRootToBazel() {
        if (!component.isPresent) return

        val sharedProperties = mutableMapOf<String, Any?>()
        setupSharedProperties(component.get(), sharedProperties)
        processModules(sharedProperties)

        processAndWrite()
    }

    @OptIn(InternalAirinApi::class)
    private fun processModules(sharedProperties: MutableMap<String, Any?>) {
        for ((_, module) in allModules.get()) {
            val moduleComponent = allComponents.get().getValue(module.packageComponentId!!)
            setupSharedProperties(moduleComponent, sharedProperties)

            val outputs = moduleComponent.invoke(module)
            for ((_, builders) in outputs.starlarkFiles) {
                for (builder in builders)
                    builder.build()
            }
        }
    }

    companion object {
        const val NAME = "migrateRootToBazel"
    }
}