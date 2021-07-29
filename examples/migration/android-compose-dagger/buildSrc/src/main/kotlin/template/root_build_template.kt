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
import org.morfly.airin.starlark.library.alias
import org.morfly.airin.starlark.library.exports_files


fun root_build_template(
    toolsDir: String,
    artifactsDir: String,
    javaToolchainTarget: String,
    kotlinToolchainTarget: String,
    roomRuntimeTarget: String,
    roomKtxTarget: String,
    kotlinReflectTarget: String,
    composePluginTarget: String,
    roomPluginLibraryTarget: String,
    debugKeystoreFile: String
    /**
     *
     */
) = BUILD.bazel {

    `package`(default_visibility = list["//visibility:public"])

    exports_files(debugKeystoreFile)

    alias(
        name = javaToolchainTarget,
        actual = "//$toolsDir/java:$javaToolchainTarget",
    )

    alias(
        name = kotlinToolchainTarget,
        actual = "//$toolsDir/kotlin:$kotlinToolchainTarget",
    )

    alias(
        name = roomRuntimeTarget,
        actual = "//$artifactsDir:$roomRuntimeTarget",
    )

    alias(
        name = roomKtxTarget,
        actual = "//$artifactsDir:$roomKtxTarget",
    )

    alias(
        name = kotlinReflectTarget,
        actual = "//$artifactsDir:$kotlinReflectTarget",
    )

    alias(
        name = composePluginTarget,
        actual = "//$toolsDir/android:$composePluginTarget",
    )

    alias(
        name = roomPluginLibraryTarget,
        actual = "//$toolsDir/android:$roomPluginLibraryTarget",
    )

    load("@dagger//:workspace_defs.bzl", "dagger_rules")

    "dagger_rules"()
}