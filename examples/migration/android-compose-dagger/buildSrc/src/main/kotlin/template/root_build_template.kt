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
import org.morfly.airin.starlark.lang.bazel
import org.morfly.airin.starlark.library.`package`
import org.morfly.airin.starlark.library.define_kt_toolchain
import org.morfly.airin.starlark.library.exports_files
import org.morfly.airin.starlark.library.default_java_toolchain


fun root_build_template(
    /**
     *
     */
) = BUILD.bazel {
    load("@io_bazel_rules_kotlin//kotlin:kotlin.bzl", "define_kt_toolchain")
    load("@bazel_tools//tools/jdk:default_java_toolchain.bzl", "default_java_toolchain")
    load("@dagger//:workspace_defs.bzl", "dagger_rules")

    `package`(default_visibility = list["//visibility:public"])

    exports_files(list["debug.keystore"])

    default_java_toolchain(
        name = "java_toolchain"
    )

    define_kt_toolchain(
        name = "kotlin_toolchain",
        api_version = "1.5",
        jvm_target = "1.8",
        language_version = "1.5",
        experimental_use_abi_jars = False,
    )

    "dagger_rules"()
}