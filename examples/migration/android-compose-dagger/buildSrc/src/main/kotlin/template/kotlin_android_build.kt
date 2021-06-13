@file:Suppress("SpellCheckingInspection")

package template

import org.morfly.airin.starlark.lang.BUILD
import org.morfly.airin.starlark.lang.Label
import org.morfly.airin.starlark.lang.bazel
import org.morfly.airin.starlark.library.android_binary
import org.morfly.airin.starlark.library.artifact
import org.morfly.airin.starlark.library.glob
import org.morfly.airin.starlark.library.kt_android_library


data class RoomInfo(
    val roomCompilerLibraryTaget: String,
    val roomRuntimeTarget: String,
    val roomKtxTarget: String
)


fun kotlin_android_build(
    targetName: String,
    packageName: String,
    hasBinary: Boolean,
    hasCompose: Boolean,
    composePluginTarget: String,
    manifestLocation: String,
    internalDeps: Set<String>,
    externalDeps: List<String>,
    exportedTargets: List<String>,
    kotlinReflectTarget: String,
    hasDagger: Boolean,
    roomDeps: RoomInfo?,
    debugKeystoreFile: String
    /**
     *
     */
) = BUILD.bazel {
    load("@io_bazel_rules_kotlin//kotlin:kotlin.bzl", "kt_android_library")
    if (hasBinary) {
        load("@rules_android//android:rules.bzl", "android_binary")
    }
    load("@rules_jvm_external//:defs.bzl", "artifact")

    kt_android_library {
        name = targetName
        srcs = glob("src/main/java/**/*.kt")
        resource_files = glob("src/main/res/**")
        custom_package = packageName
        manifest = manifestLocation
        if (hasCompose) {
            plugins = list["//:$composePluginTarget"]
        }
        if (exportedTargets.isNotEmpty()) {
            exports = exportedTargets
        }
        visibility = list["//visibility:public"]

        deps = mutableListOf<Label>().also { deps ->
            // project modules
            deps += internalDeps
            // dagger
            if (hasDagger) deps += "//:dagger"
            // compose
            if (hasCompose) deps += artifact("androidx.compose.runtime:runtime")
            // other external artifacts
            deps += externalDeps.map { artifact(it) }
            // room deps
            if (roomDeps != null) deps += listOf(
                artifact("androidx.sqlite:sqlite-framework"),
                artifact("androidx.room:room-common"),
                "//:${roomDeps.roomCompilerLibraryTaget}",
                "//:${roomDeps.roomRuntimeTarget}",
                "//:${roomDeps.roomKtxTarget}",
            )
        }
    }


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
                "versionCode" to "1"
                "versionName" to "1.0"
            },
            multidex = "native",
            debug_key = "//:$debugKeystoreFile",
            deps = list[
                    ":$targetName",

                    "//:$kotlinReflectTarget",
                    artifact("com.google.guava:guava"),
            ],
        )
}