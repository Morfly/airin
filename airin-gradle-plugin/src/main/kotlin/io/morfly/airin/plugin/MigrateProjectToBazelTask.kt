package io.morfly.airin.plugin

import io.morfly.airin.GradlePackageComponent
import io.morfly.airin.GradleProject
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction

abstract class MigrateProjectToBazelTask : BaseMigrateToBazelTask() {

    @get:Input
    @get:Optional
    abstract override val component: Property<GradlePackageComponent>

    @get:Input
    abstract override val module: Property<GradleProject>

    @get:OutputFile
    abstract override val outputFile: RegularFileProperty

    @TaskAction
    fun migrateProjectToBazel() {
        if (!component.isPresent) return

        val sharedProperties = mutableMapOf<String, Any?>()
        setupSharedProperties(component.get(), sharedProperties)

        processAndWrite()
    }

    companion object {
        const val NAME = "migrateProjectToBazel"
    }
}
