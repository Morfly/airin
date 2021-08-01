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

@file:Suppress("SpellCheckingInspection", "FunctionName")

package template

import org.morfly.airin.starlark.lang.BUILD
import org.morfly.airin.starlark.lang.Label
import org.morfly.airin.starlark.lang.bazel
import org.morfly.airin.starlark.library.android_binary
import org.morfly.airin.starlark.library.artifact
import org.morfly.airin.starlark.library.glob
import org.morfly.airin.starlark.library.kt_android_library


fun android_module_build_template(
    targetName: String,
    packageName: String,
    hasBinary: Boolean,
    hasCompose: Boolean,
    manifestLocation: String,
    internalDeps: Set<String>,
    externalDeps: List<String>,
    exportedTargets: List<String>,
    hasDagger: Boolean,
    hasRoom: Boolean,
    isPublic: Boolean,
    injectorModule: String
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
            plugins = list["//tools:jetpack_compose_compiler_plugin"]
        }
        if (exportedTargets.isNotEmpty()) {
            exports = exportedTargets
        }
        visibility =
            if (isPublic) list["//visibility:public"]
            else list["//$injectorModule:__pkg__"]

        deps = mutableListOf<Label>().also { deps ->
            // project modules
            deps += internalDeps
            // dagger
            if (hasDagger) deps += "//:dagger"
            // compose
            if (hasCompose) deps += artifact("androidx.compose.runtime:runtime")
            // other external artifacts
            deps += externalDeps.map { artifact(it) }
            // room
            if (hasRoom) {
                deps += artifact("androidx.sqlite:sqlite-framework")
                deps += "//tools:androidx_room_room_compiler_library"
            }
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
            debug_key = "//:debug.keystore",
            deps = list[
                    ":$targetName",
                    artifact("com.google.guava:guava"),
            ],
        )
}