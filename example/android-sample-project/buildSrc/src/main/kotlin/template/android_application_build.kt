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

@file:Suppress("FunctionName", "SpellCheckingInspection")

package template

import com.morlfy.airin.starlark.lang.BUILD
import com.morlfy.airin.starlark.lang.Label
import com.morlfy.airin.starlark.lang.bazel
import com.morlfy.airin.starlark.library.android_binary
import com.morlfy.airin.starlark.library.artifact
import com.morlfy.airin.starlark.library.glob
import com.morlfy.airin.starlark.library.kt_android_library


/**
 *
 */
fun android_application_build(
    targetName: String,
    packageName: String,
    moduleDependencies: List<Label>,
    artifactDependencies: List<String>
    /**
     *
     */
) = BUILD.bazel {

    load("@io_bazel_rules_kotlin//kotlin:kotlin.bzl", "kt_android_library")
    load("@rules_jvm_external//:defs.bzl", "artifact")

    kt_android_library(
        name = targetName,
        srcs = glob(
            "src/main/java/**/*.kt",
            "src/main/kotlin/**/*.kt",
        ),
        custom_package = packageName,
        manifest = "src/main/AndroidManifest.xml",
        resource_files = glob("src/main/res/**"),
        visibility = list["//visibility:public"],
        deps = moduleDependencies + artifactDependencies.map { artifact(it) }
    )

    android_binary(
        name = "${targetName}_bin",
        manifest = "src/main/AndroidManifest.xml",
        custom_package = packageName,
        resource_files = glob("src/main/res/**"),
        manifest_values = dict {
            "minSdkVersion" to "21"
            "targetSdkVersion" to "30"
        },
        visibility = list["//visibility:public"],
        deps = list[":${targetName}"]
    )
}