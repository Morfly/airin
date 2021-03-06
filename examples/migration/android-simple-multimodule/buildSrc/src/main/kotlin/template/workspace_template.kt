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

@file:Suppress("LocalVariableName", "FunctionName", "SpellCheckingInspection")

package template

import org.morfly.airin.starlark.lang.WORKSPACE
import org.morfly.airin.starlark.library.*


/**
 *
 */
fun workspace_template(
    name: String,
    artifacts: List<String>
    /**
     *
     */
) = WORKSPACE {

    workspace(name = name)

    android_sdk_repository(
        name = "androidsdk",
        api_level = 29,
        build_tools_version = "29.0.3",
    )

    load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

    // ===== kotlin =====

    val RULES_KOTLIN_VERSION by "v1.5.0-beta-2"
    val RULES_KOTLIN_SHA by "eeae65f973b70896e474c57aa7681e444d7a5446d9ec0a59bb88c59fc263ff62"

    http_archive(
        name = "io_bazel_rules_kotlin",
        sha256 = RULES_KOTLIN_SHA,
        type = "tar.gz",
        urls = list["https://github.com/bazelbuild/rules_kotlin/releases/download/%s/rules_kotlin_release.tgz" `%` RULES_KOTLIN_VERSION],
    )

    load(
        "@io_bazel_rules_kotlin//kotlin:kotlin.bzl", "kotlin_repositories", "kt_register_toolchains"
    )
    kotlin_repositories()
    kt_register_toolchains()

    // ===== rules_jvm_external =====

    val RULES_JVM_EXTERNAL_TAG by "4.0"
    val RULES_JVM_EXTERNAL_SHA by "31701ad93dbfe544d597dbe62c9a1fdd76d81d8a9150c2bf1ecf928ecdf97169"

    http_archive(
        name = "rules_jvm_external",
        strip_prefix = "rules_jvm_external-%s" `%` RULES_JVM_EXTERNAL_TAG,
        sha256 = RULES_JVM_EXTERNAL_SHA,
        url = "https://github.com/bazelbuild/rules_jvm_external/archive/%s.zip" `%` RULES_JVM_EXTERNAL_TAG,
    )

    load("@rules_jvm_external//:defs.bzl", "maven_install")

    maven_install(
        artifacts = artifacts,
        repositories = list[
                "https://maven.google.com",
                "https://repo1.maven.org/maven2"
        ],
    )
}

