@file:Suppress("LocalVariableName", "FunctionName", "SpellCheckingInspection", "UNUSED_VARIABLE")

package template

import org.morfly.airin.starlark.elements.ListReference
import org.morfly.airin.starlark.lang.WORKSPACE
import org.morfly.airin.starlark.library.*


fun root_workspace(
    artifactList: List<String>,
    composeArtifactsWithoutVersion: List<String>
    /**
     *
     */
) = WORKSPACE {

    workspace(name = "sample")

    load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

    val KOTLIN_COMPILER_VERSION by "1.5.10"
    val COMPOSE_VERSION by "1.0.0-beta08"

    val DAGGER_TAG by "2.36"
    val DAGGER_SHA by "1e6d5c64d336af2e14c089124bf2bd9d449010db02534ce820abc6a7f0429c86"

    http_archive(
        name = "dagger",
        strip_prefix = "dagger-dagger-%s" `%` DAGGER_TAG,
        sha256 = DAGGER_SHA,
        urls = list["https://github.com/google/dagger/archive/dagger-%s.zip" `%` DAGGER_TAG],
    )

    val DAGGER_ARTIFACTS = ListReference<String>("DAGGER_ARTIFACTS")
    val DAGGER_REPOSITORIES = ListReference<String>("DAGGER_REPOSITORIES")
    load("@dagger//:workspace_defs.bzl", DAGGER_ARTIFACTS.name, DAGGER_REPOSITORIES.name)

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
    load("@rules_jvm_external//:specs.bzl", "maven")


    val composeArtifacts = linkedSetOf<String>().also {
        it += composeArtifactsWithoutVersion
        it += "androidx.compose.compiler:compiler"
        it += "androidx.compose.runtime:runtime"
    }.map { "$it:%s" `%` COMPOSE_VERSION }

    maven_install(
        artifacts = DAGGER_ARTIFACTS `+` artifactList.sorted() + composeArtifacts + list[
                "com.google.guava:guava:28.1-android"
        ],
        excluded_artifacts = list[
                "org.jetbrains.kotlin:kotlin-reflect",
        ],
        override_targets = dict {
            "org.jetbrains.kotlin:kotlin-stdlib" to "@com_github_jetbrains_kotlin//:kotlin-stdlib"
            "org.jetbrains.kotlin:kotlin-stdlib-jdk7" to "@com_github_jetbrains_kotlin//:kotlin-stdlib-jdk7"
            "org.jetbrains.kotlin:kotlin-stdlib-jdk8" to "@com_github_jetbrains_kotlin//:kotlin-stdlib-jdk8"
        },
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

    val RULES_KOTLIN_VERSION by "v1.5.0-alpha-3"
    val RULES_KOTLIN_SHA by "eeae65f973b70896e474c57aa7681e444d7a5446d9ec0a59bb88c59fc263ff62"

    -"""
    #http_archive(
    #    name = "io_bazel_rules_kotlin",
    #    sha256 = RULES_KOTLIN_SHA,
    #    type = "tar.gz",
    #    urls = ["https://github.com/bazelbuild/rules_kotlin/releases/download/%s/rules_kotlin_release.tgz" % RULES_KOTLIN_VERSION],
    #)
    """.trimIndent()

    local_repository(
        name = "io_bazel_rules_kotlin",
        path = "tools/rules_kotlin_1_5",
    )

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