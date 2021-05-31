@file:Suppress("FunctionName", "SpellCheckingInspection")

package org.morfly.airin.sample.template.other


fun android_databinding_bazelrc(
    androidToolsLocation: String,
    disableStrictJavaDeps: Boolean
    /**
     *
     */
) = """
    build --android_databinding_use_v3_4_args
    build --experimental_android_databinding_v2
    build --override_repository=android_tools=$androidToolsLocation
    build --android_databinding_use_androidx
    build --strategy=Desugar=sandboxed
    build --java_header_compilation=false
    build --verbose_failures
    build --disk_cache=bazel-cache
    build --define=android_incremental_dexing_tool=d8_dexbuilder
    build --java_toolchain=@bazel_tools//tools/jdk:toolchain_java8
    ${if (disableStrictJavaDeps) "build --strict_java_deps=OFF" else ""}

    mobile-install --android_databinding_use_v3_4_args
    mobile-install --experimental_android_databinding_v2
    mobile-install --override_repository=android_tools=$androidToolsLocation
    mobile-install --android_databinding_use_androidx
    mobile-install --strategy=Desugar=sandboxed
    mobile-install --java_header_compilation=false
    mobile-install --verbose_failures
    mobile-install --disk_cache=bazel-cache
    mobile-install --define=android_incremental_dexing_tool=d8_dexbuilder
    mobile-install --java_toolchain=@bazel_tools//tools/jdk:toolchain_java8
    ${if (disableStrictJavaDeps) "mobile-install --strict_java_deps=OFF" else ""}
    mobile-install --start_app

    try-import local.bazelrc
""".trimIndent()