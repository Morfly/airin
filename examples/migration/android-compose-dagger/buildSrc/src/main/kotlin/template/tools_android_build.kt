@file:Suppress("FunctionName")

package template

import org.morfly.airin.starlark.lang.BUILD
import org.morfly.airin.starlark.library.artifact
import org.morfly.airin.starlark.library.java_library
import org.morfly.airin.starlark.library.java_plugin
import org.morfly.airin.starlark.library.kt_compiler_plugin


fun tools_android_build(
    /**
     *
     */
) = BUILD("tools/android") {

    load("@io_bazel_rules_kotlin//kotlin:kotlin.bzl", "kt_compiler_plugin")
    load("@rules_jvm_external//:defs.bzl", "artifact")

    kt_compiler_plugin(
        name = "jetpack_compose_compiler_plugin",
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
        name = "androidx_room_room_compiler_library",
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