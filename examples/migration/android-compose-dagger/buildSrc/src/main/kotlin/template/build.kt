@file:Suppress("SpellCheckingInspection")

package template

import org.morfly.airin.starlark.lang.BUILD
import org.morfly.airin.starlark.lang.bazel
import org.morfly.airin.starlark.library.android_binary
import org.morfly.airin.starlark.library.artifact
import org.morfly.airin.starlark.library.glob
import org.morfly.airin.starlark.library.kt_android_library


fun build(
    targetName: String,
    packageName: String,
    hasBinary: Boolean,
    composePlugin: String,
    manifestLocation: String,
    internalDeps: List<String>,
    externalDeps: List<String>,
    /**
     *
     */
) = BUILD.bazel {
    load("@io_bazel_rules_kotlin//kotlin:kotlin.bzl", "kt_android_library")
    load("@rules_android//android:rules.bzl", "android_binary")
    load("@rules_jvm_external//:defs.bzl", "artifact")

    kt_android_library(
        name = targetName,
        srcs = glob(list["src/main/java/**/*.kt"]),
        resource_files = glob(list["src/main/res/**"]),
        custom_package = packageName,
        manifest = manifestLocation,
        plugins = list[composePlugin],
        visibility = list["//visibility:public"],
        deps = internalDeps + externalDeps.map { artifact(it) }
    )


    if (hasBinary)

        android_binary(
            name = "bin",
            custom_package = packageName,
            manifest = manifestLocation,
            visibility = list["//visibility:public"],
            incremental_dexing = 1,
            manifest_values = dict {
                "minSdkVersion" to "21"
                "targetSdkVersion" to "29"
            },
            multidex = "native",
            deps = list[
                    ":$targetName",

                    "//:kotlin_reflect",
                    artifact("com.google.guava:guava"),
            ],
        )
}