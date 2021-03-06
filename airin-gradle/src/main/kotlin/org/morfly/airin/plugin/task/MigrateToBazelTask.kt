/*
 * Copyright 2021 Pavlo Stavytskyi
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

package org.morfly.airin.plugin.task

import org.morfly.airin.GradleMigratorToBazel
import org.morfly.airin.plugin.dsl.AirinExtension
import org.morfly.airin.starlark.writer.StarlarkFileWriter
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import javax.inject.Inject
import kotlin.system.measureTimeMillis


/**
 *
 */
open class MigrateToBazelTask @Inject constructor(
    extension: AirinExtension
) : DefaultTask() {

    private val migrator = GradleMigratorToBazel(extension, StarlarkFileWriter)

    @TaskAction
    fun migrateToBazel() {
        val millis = measureTimeMillis {
            migrator.migrate(project.rootProject)
        }
        logger.lifecycle("\nMIGRATION SUCCESSFUL in ${millis / 1000.0}s")
    }

    companion object {
        const val NAME = "migrateToBazel"
    }
}