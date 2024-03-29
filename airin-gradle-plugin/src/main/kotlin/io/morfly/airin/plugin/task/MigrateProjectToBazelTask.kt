/*
 * Copyright 2023 Pavlo Stavytskyi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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