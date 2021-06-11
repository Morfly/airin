package template

import org.morfly.airin.starlark.lang.BUILD


fun tools_java_build(
    /**
     *
     */
) = BUILD("tools/java") {

    load("@bazel_tools//tools/jdk:default_java_toolchain.bzl", "default_java_toolchain")

    "default_java_toolchain" {
        "name" `=` "java_toolchain"
        "visibility" `=` list["//:__pkg__"]
    }
}