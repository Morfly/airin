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

package io.morfly.airin.module

import io.morfly.airin.ModuleComponent
import io.morfly.airin.GradleModule
import io.morfly.airin.ModuleContext
import org.gradle.api.Project

abstract class JvmLibraryModule : ModuleComponent() {

    override fun canProcess(project: Project): Boolean {
        return false
    }

    override fun ModuleContext.onInvoke(module: GradleModule) {

    }
}
