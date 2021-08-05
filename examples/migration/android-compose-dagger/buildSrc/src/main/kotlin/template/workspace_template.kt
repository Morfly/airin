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

@file:Suppress("LocalVariableName", "FunctionName", "SpellCheckingInspection", "UNUSED_VARIABLE")

package template

import org.morfly.airin.starlark.lang.StringType
import org.morfly.airin.starlark.lang.WORKSPACE
import org.morfly.airin.starlark.library.*


fun workspace_template(
    artifactList: List<String>,
    composeArtifactsWithoutVersion: List<String>
    /**
     *
     */
) = WORKSPACE {

    workspace(name = "android-compose-dagger")

    val KOTLIN_COMPILER_VERSION by "1.5.10"
    val COMPOSE_VERSION by "1.0.0"

    load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

    val RULES_JAVA_VERSION by "4.0.0"
    val RULES_JAVA_SHA by "34b41ec683e67253043ab1a3d1e8b7c61e4e8edefbcad485381328c934d072fe"

    http_archive(
        name = "rules_java",
        url = "https://github.com/bazelbuild/rules_java/releases/download/{v}/rules_java-{v}.tar.gz".format { "v" `=` RULES_JAVA_VERSION },
        sha256 = RULES_JAVA_SHA,
    )

    load("@rules_java//java:repositories.bzl", "rules_java_dependencies", "rules_java_toolchains")
    "rules_java_dependencies"()
    "rules_java_toolchains"()

    val DAGGER_TAG by "2.36"
    val DAGGER_SHA by "1e6d5c64d336af2e14c089124bf2bd9d449010db02534ce820abc6a7f0429c86"

    http_archive(
        name = "dagger",
        strip_prefix = "dagger-dagger-%s" `%` DAGGER_TAG,
        sha256 = DAGGER_SHA,
        urls = list["https://github.com/google/dagger/archive/dagger-%s.zip" `%` DAGGER_TAG],
    )

    val (DAGGER_ARTIFACTS, DAGGER_REPOSITORIES) = load(
        "@dagger//:workspace_defs.bzl",
        "DAGGER_ARTIFACTS", "DAGGER_REPOSITORIES"
    ).v<List<StringType>, List<StringType>>()

    val RULES_JVM_EXTERNAL_VERSION by "4.1"
    val RULES_JVM_EXTERNAL_SHA by "f36441aa876c4f6427bfb2d1f2d723b48e9d930b62662bf723ddfb8fc80f0140"

    http_archive(
        name = "rules_jvm_external",
        sha256 = RULES_JVM_EXTERNAL_SHA,
        strip_prefix = "rules_jvm_external-%s" `%` RULES_JVM_EXTERNAL_VERSION,
        urls = list[
                "https://github.com/bazelbuild/rules_jvm_external/archive/%s.zip" `%` RULES_JVM_EXTERNAL_VERSION,
        ],
    )

    load("@rules_jvm_external//:defs.bzl", "maven_install")

    val composeArtifacts = linkedSetOf<String>().also {
        it += composeArtifactsWithoutVersion
        it += "androidx.compose.compiler:compiler"
        it += "androidx.compose.runtime:runtime"
    }.map { "$it:%s" `%` COMPOSE_VERSION }

    maven_install(
        artifacts = DAGGER_ARTIFACTS `+` artifactList.sorted() + composeArtifacts + list[
                "com.google.guava:guava:28.1-android"
        ],
        override_targets = dict {
            "org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm" to "@//third_party:kotlinx_coroutines_core_jvm"
            "org.jetbrains.kotlin:kotlin-reflect" to "@//third_party:kotlin_reflect"
            "androidx.room:room-runtime" to "@//third_party:room_runtime"
        },
        repositories = DAGGER_REPOSITORIES `+` list[
                "https://maven.google.com",
                "https://repo1.maven.org/maven2",
        ],
    )

    maven_install(
        name = "maven_secondary",
        artifacts = list[
                "org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.5.1",
                "androidx.room:room-runtime:2.3.0",
        ],
        repositories = list[
                "https://maven.google.com",
                "https://repo1.maven.org/maven2",
        ]
    )

    val RULES_ANDROID_VERSION by "0.1.1"
    val RULES_ANDROID_SHA by "cd06d15dd8bb59926e4d65f9003bfc20f9da4b2519985c27e190cddc8b7a7806"

    http_archive(
        name = "rules_android",
        sha256 = RULES_ANDROID_SHA,
        strip_prefix = "rules_android-%s" `%` RULES_ANDROID_VERSION,
        urls = list[
                "https://github.com/bazelbuild/rules_android/archive/v%s.zip" `%` RULES_ANDROID_VERSION,
        ],
    )

    load("@rules_android//android:rules.bzl", "android_sdk_repository")

    android_sdk_repository(
        name = "androidsdk",
        api_level = 29,
        build_tools_version = "29.0.3",
    )

    val RULES_KOTLIN_VERSION by "c26007a1776a79d94bea7c257ef07a23bbc998d5"
    val RULES_KOTLIN_SHA by "be7b1fac4f93fbb81eb79f2f44caa97e1dfa69d2734e4e184443acd9f5182386"

    http_archive(
        name = "io_bazel_rules_kotlin",
        sha256 = RULES_KOTLIN_SHA,
        strip_prefix = "rules_kotlin-%s" `%` RULES_KOTLIN_VERSION,
        urls = list[
                "https://github.com/bazelbuild/rules_kotlin/archive/%s.tar.gz" `%` RULES_KOTLIN_VERSION,
        ],
    )

    load("@io_bazel_rules_kotlin//kotlin:dependencies.bzl", "kt_download_local_dev_dependencies")
    "kt_download_local_dev_dependencies"()

    load("@io_bazel_rules_kotlin//kotlin:kotlin.bzl", "kotlin_repositories")

    val RULES_KOTLIN_COMPILER_RELEASE by dict {
        "urls" to list[
                "https://github.com/JetBrains/kotlin/releases/download/v{v}/kotlin-compiler-{v}.zip".format { "v" `=` KOTLIN_COMPILER_VERSION },
        ]
        "sha256" to "2f8de1d73b816354055ff6a4b974b711c11ad55a68b948ed30b38155706b3c4e"
    }

    kotlin_repositories(
        compiler_release = RULES_KOTLIN_COMPILER_RELEASE,
    )

    register_toolchains("//:kotlin_toolchain")
}