package com.morfly.airin.template.java

import org.morfly.bazelgen.generator.dsl.BUILD
import org.morfly.bazelgen.generator.dsl.bazel
import org.morfly.bazelgen.generator.dsl.function.java_binary


/**
 *
 */
fun java_root_build_file(
    binaryName: String,
    mainClass: String,
    dependencies: List<String> = emptyList()
    /**
     *
     */
) = BUILD.bazel {
    load("@rules_java//java:defs.bzl", "java_library")

    java_binary {
        name = binaryName
        main_class = mainClass
        runtime_deps = dependencies
    }
}