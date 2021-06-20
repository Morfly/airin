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

@file:Suppress("FunctionName")

package template

import org.morfly.airin.starlark.lang.BUILD


internal fun tools_java_build(
    toolsDir: String,
    javaToolchainTargetName: String
    /**
     *
     */
) = BUILD("$toolsDir/java") {

    load("@bazel_tools//tools/jdk:default_java_toolchain.bzl", "default_java_toolchain")

    "default_java_toolchain" {
        "name" `=` javaToolchainTargetName
        "visibility" `=` list["//:__pkg__"]
    }
}