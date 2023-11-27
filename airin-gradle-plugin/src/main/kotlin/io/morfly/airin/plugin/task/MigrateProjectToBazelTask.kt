package io.morfly.airin.plugin.task

import io.morfly.airin.ComponentId
import io.morfly.airin.GradleModule
import io.morfly.airin.ModuleComponent
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.provider.MapProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputFiles
import org.gradle.api.tasks.TaskAction

abstract class MigrateProjectToBazelTask : AbstractMigrateToBazelTask() {

    @get:Input
    @get:Optional
    abstract override val component: Property<ModuleComponent>

    @get:Input
    @get:Optional
    abstract val componentProperties: MapProperty<ComponentId, Map<String, Any>>

    @get:Input
    abstract override val module: Property<GradleModule>

    @get:Input
    abstract val properties: MapProperty<String, Any>

    @get:OutputFiles
    abstract override val outputFiles: ConfigurableFileCollection

    @TaskAction
    fun migrateProjectToBazel() {
        if (!component.isPresent) return

        val sharedProperties = mutableMapOf<String, Any>()
        processAndWrite(sharedProperties)
    }

    companion object {
        const val NAME = "migrateProjectToBazel"
    }
}