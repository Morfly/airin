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

@file:Suppress("SpellCheckingInspection")

package org.morfly.airin.starlark.workspace

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import org.morfly.airin.starlark.TestWriter


class WorkspaceTest : ShouldSpec({
    val writer = TestWriter()

    context("workspace") {
        val file = workspace()

        val expectedResult = """
            workspace(name = "android-databinding")

            android_sdk_repository(
                name = "androidsdk",
                api_level = 29,
                build_tools_version = "29.0.3",
            )

            load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

            RULES_JVM_EXTERNAL_TAG = "3.3"
            RULES_JVM_EXTERNAL_SHA = "d85951a92c0908c80bd8551002d66cb23c3434409c814179c0ff026b53544dab"

            http_archive(
                name = "rules_jvm_external",
                sha256 = RULES_JVM_EXTERNAL_SHA,
                strip_prefix = "rules_jvm_external-%s" % RULES_JVM_EXTERNAL_TAG,
                url = "https://github.com/bazelbuild/rules_jvm_external/archive/%s.zip" % RULES_JVM_EXTERNAL_TAG,
            )

            DAGGER_TAG = "2.28.1"
            DAGGER_SHA = "9e69ab2f9a47e0f74e71fe49098bea908c528aa02fa0c5995334447b310d0cdd"

            http_archive(
                name = "dagger",
                sha256 = DAGGER_SHA,
                strip_prefix = "dagger-dagger-%s" % DAGGER_TAG,
                urls = ["https://github.com/google/dagger/archive/dagger-%s.zip" % DAGGER_TAG],
            )

            load(
                "@dagger//:workspace_defs.bzl",
                "DAGGER_ARTIFACTS",
                "DAGGER_REPOSITORIES",
            )
            load("@rules_jvm_external//:defs.bzl", "maven_install")

            maven_install(
                artifacts = DAGGER_ARTIFACTS + [
                    "androidx.databinding:databinding-adapters:3.4.2",
                    "androidx.databinding:databinding-common:3.4.2",
                    "androidx.databinding:databinding-compiler:3.4.2",
                    "androidx.databinding:databinding-runtime:3.4.2",
                    "androidx.annotation:annotation:1.1.0",
                ],
                repositories = DAGGER_REPOSITORIES + [
                    "https://repo1.maven.org/maven2",
                    "https://maven.google.com",
                ],
                fail_on_missing_checksum = False,
                jetify = True,
            )

            bind(
                name = "databinding_annotation_processor",
                actual = "//tools/android:compiler_annotation_processor",
            )

            load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

            RULES_KOTLIN_VERSION = "legacy-1.4.0-rc3"
            RULES_KOTLIN_SHA = "da0e6e1543fcc79e93d4d93c3333378f3bd5d29e82c1bc2518de0dbe048e6598"

            http_archive(
                name = "io_bazel_rules_kotlin",
                sha256 = RULES_KOTLIN_SHA,
                url = "https://github.com/bazelbuild/rules_kotlin/releases/download/%s/rules_kotlin_release.tgz" % RULES_KOTLIN_VERSION,
            )

            load(
                "@io_bazel_rules_kotlin//kotlin:kotlin.bzl",
                "kotlin_repositories",
                "kt_register_toolchains",
            )

            KOTLIN_VERSION = "1.3.72"
            KOTLINC_RELEASE_SHA = "ccd0db87981f1c0e3f209a1a4acb6778f14e63fe3e561a98948b5317e526cc6c"
            KOTLINC_RELEASE = {
                "urls": ["https://github.com/JetBrains/kotlin/releases/download/v{v}/kotlin-compiler-{v}.zip".format(v = KOTLIN_VERSION)],
                "sha256": KOTLINC_RELEASE_SHA,
            }

            kotlin_repositories(compiler_release = KOTLINC_RELEASE)

            kt_register_toolchains()
        """.trimIndent()

        writer.write(file) shouldBe expectedResult
    }
})