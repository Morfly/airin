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
import org.morfly.airin.starlark.library.`package`
import org.morfly.airin.starlark.library.artifact
import org.morfly.airin.starlark.library.java_import
import org.morfly.airin.starlark.library.java_library


fun third_party_build_template(
    /**
     *
     */
) = BUILD("third_party") {
    load("@rules_java//java:defs.bzl", "java_library", "java_import")
    load("@rules_jvm_external//:defs.bzl", "artifact")

    `package`(default_visibility = list["//visibility:public"])

    java_library(
        name = "sun_misc_stubs",
        srcs = list[
                "sun/misc/Signal.java",
                "sun/misc/SignalHandler.java",
        ],
        visibility = list["//visibility:private"],
        neverlink = True,
    )

    java_import(
        name = "kotlinx_coroutines_core_jvm",
        jars = list["@maven_secondary//:v1/https/repo1.maven.org/maven2/org/jetbrains/kotlinx/kotlinx-coroutines-core-jvm/1.5.1/kotlinx-coroutines-core-jvm-1.5.1.jar"],
        deps = list[
                ":sun_misc_stubs",
                artifact("org.jetbrains.kotlin:kotlin-stdlib-common"),
                artifact("org.jetbrains.kotlin:kotlin-stdlib-jdk8"),
        ],
    )
}