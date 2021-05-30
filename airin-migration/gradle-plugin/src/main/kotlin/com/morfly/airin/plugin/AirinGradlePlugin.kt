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

package com.morfly.airin.plugin

import com.morfly.airin.plugin.dsl.AirinExtension
import com.morfly.airin.plugin.task.MigrateToBazelTask
import org.gradle.api.Plugin
import org.gradle.api.Project


/**
 *
 */
class AirinGradlePlugin : Plugin<Project> {

    override fun apply(target: Project) {
        val extension = with(target.extensions) {
            create(AirinExtension.NAME, AirinExtension::class.java, target.objects)
        }
        with(target.tasks) {
            register(MigrateToBazelTask.NAME, MigrateToBazelTask::class.java, extension)
        }
    }
}