@file:Suppress("LocalVariableName")

package template

import org.morfly.airin.starlark.lang.WORKSPACE
import org.morfly.airin.starlark.lang.bazel
import org.morfly.airin.starlark.library.*


fun workspace_template(
    name: String,
    artifactDeps: List<String>
    /**
     *
     */
) = WORKSPACE.bazel {
    workspace(name = name)

    load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

    val DAGGER_TAG by "2.36"
    val DAGGER_SHA by "1e6d5c64d336af2e14c089124bf2bd9d449010db02534ce820abc6a7f0429c86"

    http_archive(
        name = "dagger",
        sha256 = DAGGER_SHA,
        strip_prefix = "dagger-dagger-%s" `%` DAGGER_TAG,
        urls = list["https://github.com/google/dagger/archive/dagger-%s.zip" `%` DAGGER_TAG],
    )

    val (DAGGER_ARTIFACTS, DAGGER_REPOSITORIES) = load(
        "@dagger//:workspace_defs.bzl", "DAGGER_ARTIFACTS", "DAGGER_REPOSITORIES"
    ).v<List<String>, List<String>>()

    val RULES_JVM_EXTERNAL_VERSION by "4.1"
    val RULES_JVM_EXTERNAL_SHA by "f36441aa876c4f6427bfb2d1f2d723b48e9d930b62662bf723ddfb8fc80f0140"

    http_archive(
        name = "rules_jvm_external",
        sha256 = RULES_JVM_EXTERNAL_SHA,
        strip_prefix = "rules_jvm_external-%s" `%` RULES_JVM_EXTERNAL_VERSION,
        urls = list["https://github.com/bazelbuild/rules_jvm_external/archive/%s.zip" `%` RULES_JVM_EXTERNAL_VERSION],
    )

    load("@rules_jvm_external//:defs.bzl", "maven_install")

    maven_install(
        artifacts = DAGGER_ARTIFACTS `+` artifactDeps,
        excluded_artifacts = list[
                "org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm",
                "org.jetbrains.kotlinx:kotlinx-coroutines-android",
                "org.jetbrains.kotlinx:kotlinx-coroutines-core",
        ],
        repositories = DAGGER_REPOSITORIES `+` list[
                "https://maven.google.com",
                "https://repo1.maven.org/maven2",
        ],
    )

    val RULES_ANDROID_VERSION by "0.1.1"
    val RULES_ANDROID_SHA by "cd06d15dd8bb59926e4d65f9003bfc20f9da4b2519985c27e190cddc8b7a7806"

    http_archive(
        name = "rules_android",
        sha256 = RULES_ANDROID_SHA,
        strip_prefix = "rules_android-%s" `%` RULES_ANDROID_VERSION,
        urls = list["https://github.com/bazelbuild/rules_android/archive/v%s.zip" `%` RULES_ANDROID_VERSION],
    )

    load("@rules_android//android:rules.bzl", "android_sdk_repository")

    android_sdk_repository(
        name = "androidsdk",
        api_level = 29,
        build_tools_version = "29.0.3",
    )

    val RULES_KOTLIN_VERSION by "v1.5.0-beta-2"
    val RULES_KOTLIN_SHA by "eeae65f973b70896e474c57aa7681e444d7a5446d9ec0a59bb88c59fc263ff62"

    http_archive(
        name = "io_bazel_rules_kotlin",
        sha256 = RULES_KOTLIN_SHA,
        type = "tar.gz",
        urls = list["https://github.com/bazelbuild/rules_kotlin/releases/download/%s/rules_kotlin_release.tgz" `%` RULES_KOTLIN_VERSION],
    )

    load(
        "@io_bazel_rules_kotlin//kotlin:kotlin.bzl",
        "kotlin_repositories",
        "kt_register_toolchains"
    )
    kotlin_repositories()
    kt_register_toolchains()
}