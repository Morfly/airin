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

@file:Suppress("FunctionName", "SpellCheckingInspection")

package template

import org.morfly.airin.starlark.lang.BUILD
import org.morfly.airin.starlark.library.artifact
import org.morfly.airin.starlark.library.java_library
import org.morfly.airin.starlark.library.java_plugin
import org.morfly.airin.starlark.library.kt_compiler_plugin


internal fun tools_android_build(
    toolsDir: String,
    composePluginTargetName: String,
    roomPluginLibraryTargetName: String,
    /**
     *
     */
) = BUILD("$toolsDir/android") {

    load("@io_bazel_rules_kotlin//kotlin:kotlin.bzl", "kt_compiler_plugin")
    load("@rules_jvm_external//:defs.bzl", "artifact")

    kt_compiler_plugin(
        name = composePluginTargetName,
        id = "androidx.compose.compiler",
        target_embedded_compiler = True,
        deps = list[
                artifact("androidx.compose.compiler:compiler"),
        ],
        visibility = list["//:__pkg__"],
    )

    java_plugin(
        name = "androidx_room_room_compiler_plugin",
        processor_class = "androidx.room.RoomProcessor",
        deps = list[
                artifact("androidx.room:room-compiler"),
        ],
        neverlink = True,
    )

    java_library(
        name = roomPluginLibraryTargetName,
        exported_plugins = list[
                ":androidx_room_room_compiler_plugin",
        ],
        neverlink = True,
        exports = list[
                artifact("androidx.room:room-common"),
                artifact("androidx.room:room-compiler"),
                artifact("androidx.room:room-runtime"),
                artifact("androidx.room:room-ktx"),
        ],
        visibility = list["//:__pkg__"],
    )
}