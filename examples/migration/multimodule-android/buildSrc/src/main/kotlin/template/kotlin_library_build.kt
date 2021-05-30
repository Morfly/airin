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
import com.morlfy.airin.starlark.library.artifact
import com.morlfy.airin.starlark.library.glob
import com.morlfy.airin.starlark.library.kt_jvm_library


/**
 *
 */
fun kotlin_library_build(
    targetName: String,
    moduleDependencies: List<Label>,
    artifactDependencies: List<String>
    /**
     *
     */
) = BUILD.bazel {
    load("@io_bazel_rules_kotlin//kotlin:kotlin.bzl", "kt_jvm_library")
    load("@rules_jvm_external//:defs.bzl", "artifact")

    kt_jvm_library(
        name = targetName,
        srcs = glob(
            "src/main/java/**/*.kt",
            "src/main/kotlin/**/*.kt",
        ),
        visibility = list["//visibility:public"],
        deps = moduleDependencies + artifactDependencies.map { artifact(it) }
    )
}