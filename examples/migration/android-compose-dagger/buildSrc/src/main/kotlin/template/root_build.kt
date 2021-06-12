@file:Suppress("FunctionName", "SpellCheckingInspection")

package template

import org.morfly.airin.starlark.lang.BUILD
import org.morfly.airin.starlark.lang.bazel
import org.morfly.airin.starlark.library.`package`
import org.morfly.airin.starlark.library.alias


fun root_build(
    toolsDir: String,
    artifactsDir: String,
    javaToolchainTarget: String,
    kotlinToolchainTarget: String,
    roomRuntimeTarget: String,
    roomKtxTarget: String,
    kotlinReflectTarget: String,
    composePluginTarget: String,
    roomPluginLibraryTarget: String,
    /**
     *
     */
) = BUILD.bazel {

    `package`(default_visibility = list["//visibility:public"])

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