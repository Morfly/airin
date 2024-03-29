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

package io.morfly.airin

import io.morfly.pendant.starlark.lang.context.FileContext

class ModuleContext(
    override val sharedProperties: MutableMap<String, Any> = mutableMapOf()
) : SharedPropertiesHolder {

    val starlarkFiles = mutableMapOf<String, MutableList<FileContext>>()

    /**
     * Registers a Bazel file to be generated during the migration.
     *
     * @param relativeDirPath - denotes a file path relative to the module location.
     */
    fun generate(vararg files: FileContext, relativeDirPath: String = "") {
        for (file in files) {
            starlarkFiles.getOrPut(relativeDirPath.fixPath(), ::mutableListOf) += file
        }
    }
}
