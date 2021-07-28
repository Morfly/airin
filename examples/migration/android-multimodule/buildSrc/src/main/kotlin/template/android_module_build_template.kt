package template

import org.morfly.airin.starlark.lang.BUILD
import org.morfly.airin.starlark.lang.bazel
import org.morfly.airin.starlark.library.android_binary
import org.morfly.airin.starlark.library.artifact
import org.morfly.airin.starlark.library.glob
import org.morfly.airin.starlark.library.kt_android_library


fun android_module_build_template(
    name: String,
    packageName: String,
    artifactDeps: List<String>,
    moduleDeps: List<String>,
    hasBinary: Boolean,
    /**
     *
     */
) = BUILD.bazel {
    load("@io_bazel_rules_kotlin//kotlin:kotlin.bzl", "kt_android_library")
    load("@rules_android//android:rules.bzl", "android_binary")
    load("@rules_jvm_external//:defs.bzl", "artifact")

    kt_android_library(
        name = name,
        srcs = glob("src/main/java/**/*.kt"),
        resource_files = glob("src/main/res/**"),
        custom_package = packageName,
        manifest = "src/main/AndroidManifest.xml",
        visibility = list["//visibility:public"],
        deps = moduleDeps + artifactDeps.map(::artifact)
    )

    if (hasBinary)
        android_binary(
            name = "bin",
            custom_package = "org.morfly.sample",
            manifest = "src/main/AndroidManifest.xml",
            manifest_values = dict {
                "minSdkVersion" to "21"
                "targetSdkVersion" to "29"
                "versionCode" to "1"
                "versionName" to "1.0"
            },
            multidex = "native",
            incremental_dexing = 1,
            debug_key = "debug.keystore",
            visibility = list["//visibility:public"],
            deps = list[":$name"],
        )
}