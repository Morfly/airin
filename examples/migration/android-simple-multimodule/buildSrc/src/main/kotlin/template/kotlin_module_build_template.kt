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

import org.morfly.airin.starlark.lang.BUILD
import org.morfly.airin.starlark.lang.Label
import org.morfly.airin.starlark.lang.bazel
import org.morfly.airin.starlark.library.artifact
import org.morfly.airin.starlark.library.glob
import org.morfly.airin.starlark.library.kt_jvm_library


/**
 *
 */
fun kotlin_module_build_template(
    name: String,
    moduleDependencies: List<Label>,
    artifactDependencies: List<String>
    /**
     *
     */
) = BUILD.bazel {
    load("@io_bazel_rules_kotlin//kotlin:kotlin.bzl", "kt_jvm_library")
    load("@rules_jvm_external//:defs.bzl", "artifact")

    kt_jvm_library(
        name = name,
        srcs = glob(
            "src/main/java/**/*.kt",
            "src/main/kotlin/**/*.kt",
        ),
        visibility = list["//visibility:public"],
        deps = moduleDependencies + artifactDependencies.map { artifact(it) }
    )
}