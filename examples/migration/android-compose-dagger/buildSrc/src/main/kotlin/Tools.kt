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

import org.gradle.api.Project
import org.morfly.airin.GradleStandaloneTemplateProvider
import org.morfly.airin.starlark.elements.StarlarkFile
import template.tools_android_build
import template.tools_java_build
import template.tools_kotlin_build


class Tools : GradleStandaloneTemplateProvider() {

    override fun provide(target: Project, relativePath: String): List<StarlarkFile> = listOf(
        tools_java_build(
            toolsDir = TOOLS_DIR,
            javaToolchainTargetName = JAVA_TOOLCHAIN_TARGET
        ),
        tools_kotlin_build(
            toolsDir = TOOLS_DIR,
            kotlinToolchainTargetName = KOTLIN_TOOLCHAIN_TARGET,
            kotlinVersion = KOTLIN_LANG_VERSION
        ),
        tools_android_build(
            toolsDir = TOOLS_DIR,
            composePluginTargetName = COMPOSE_PLUGIN_TARGET,
            roomPluginLibraryTargetName = ROOM_PLUGIN_LIBRARY_TARGET
        )
    )

    companion object {
        const val TOOLS_DIR = "tools"
        const val KOTLIN_LANG_VERSION = "1.5"

        const val JAVA_TOOLCHAIN_TARGET = "java_toolchain"
        const val KOTLIN_TOOLCHAIN_TARGET = "kotlin_toolchain"
        const val COMPOSE_PLUGIN_TARGET = "jetpack_compose_compiler_plugin"
        const val ROOM_PLUGIN_LIBRARY_TARGET = "androidx_room_room_compiler_library"
    }
}