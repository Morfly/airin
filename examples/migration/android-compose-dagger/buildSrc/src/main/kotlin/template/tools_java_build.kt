@file:Suppress("FunctionName")

package template

import org.morfly.airin.starlark.lang.BUILD


fun tools_java_build(
    toolsDir: String,
    javaToolchainTargetName: String
    /**
     *
     */
) = BUILD("$toolsDir/java") {

    load("@bazel_tools//tools/jdk:default_java_toolchain.bzl", "default_java_toolchain")

    "default_java_toolchain" {
        "name" `=` javaToolchainTargetName
        "visibility" `=` list["//:__pkg__"]
    }
}