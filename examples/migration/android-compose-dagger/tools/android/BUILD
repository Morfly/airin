load("@io_bazel_rules_kotlin//kotlin:kotlin.bzl", "kt_compiler_plugin")
load("@rules_jvm_external//:defs.bzl", "artifact")

kt_compiler_plugin(
    name = "jetpack_compose_compiler_plugin",
    deps = [artifact("androidx.compose.compiler:compiler")],
    id = "androidx.compose.compiler",
    target_embedded_compiler = True,
    visibility = ["//:__pkg__"],
)

java_plugin(
    name = "androidx_room_room_compiler_plugin",
    processor_class = "androidx.room.RoomProcessor",
    deps = [artifact("androidx.room:room-compiler")],
    neverlink = True,
)

java_library(
    name = "androidx_room_room_compiler_library",
    exports = [
        artifact("androidx.room:room-common"),
        artifact("androidx.room:room-compiler"),
        artifact("androidx.room:room-runtime"),
        artifact("androidx.room:room-ktx"),
    ],
    exported_plugins = [":androidx_room_room_compiler_plugin"],
    visibility = ["//:__pkg__"],
    neverlink = True,
)