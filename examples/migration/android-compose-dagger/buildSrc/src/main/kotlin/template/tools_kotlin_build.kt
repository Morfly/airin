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
import org.morfly.airin.starlark.library.define_kt_toolchain
import org.morfly.airin.starlark.library.kt_javac_options
import org.morfly.airin.starlark.library.kt_kotlinc_options


internal fun tools_kotlin_build(
    toolsDir: String,
    kotlinToolchainTargetName: String,
    kotlinVersion: String
    /**
     *
     */
) = BUILD("$toolsDir/kotlin") {

    load(
        "@io_bazel_rules_kotlin//kotlin:kotlin.bzl",
        "define_kt_toolchain",
        "kt_javac_options",
        "kt_kotlinc_options",
    )

    kt_kotlinc_options {
        name = "kt_kotlinc_options"
//        "x_allow_jvm_ir_dependencies" `=` True
//        "x_use_ir" `=` True
    }

    kt_javac_options {
        name = "kt_javac_options"
    }

    define_kt_toolchain(
        name = kotlinToolchainTargetName,
        api_version = kotlinVersion,
        experimental_use_abi_jars = False,
        javac_options = ":kt_javac_options",
        jvm_target = "1.8",
        kotlinc_options = ":kt_kotlinc_options",
        language_version = kotlinVersion,
    )
}