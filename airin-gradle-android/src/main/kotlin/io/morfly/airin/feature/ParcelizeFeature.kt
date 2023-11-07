package io.morfly.airin.feature

import io.morfly.airin.FeatureContext
import io.morfly.airin.GradleFeatureComponent
import io.morfly.airin.GradleModule
import io.morfly.airin.module.AndroidLibraryModule
import io.morfly.airin.module.RootModule
import io.morfly.airin.plugin.AirinAndroidGradlePlugin
import io.morfly.pendant.starlark.KtAndroidLibraryContext
import io.morfly.pendant.starlark.kt_compiler_plugin
import io.morfly.pendant.starlark.kt_jvm_library
import io.morfly.pendant.starlark.lang.context.BuildContext
import io.morfly.pendant.starlark.lang.onContext
import org.gradle.api.Project

abstract class ParcelizeFeature : GradleFeatureComponent() {

    override fun canProcess(target: Project): Boolean = with(target.plugins) {
        hasPlugin("org.jetbrains.kotlin.plugin.parcelize") || hasPlugin(AirinAndroidGradlePlugin.ID)
    }

    override fun FeatureContext.onInvoke(packageDescriptor: GradleModule) {
        onContext<BuildContext>(id = RootModule.ID_THIRD_PARTY_BUILD) {

            load("@io_bazel_rules_kotlin//kotlin:core.bzl", "kt_compiler_plugin")
            load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

            kt_compiler_plugin(
                name = "parcelize_plugin",
                compile_phase = True,
                id = "org.jetbrains.kotlin.parcelize",
                stubs_phase = True,
                deps = list["@com_github_jetbrains_kotlin//:parcelize-compiler-plugin"],
            )

            kt_jvm_library(
                name = "parcelize",
                srcs = list(),
                exported_compiler_plugins = list[":parcelize_plugin"],
                plugins = list[":parcelize_plugin"],
                visibility = list["//visibility:public"],
                exports = list["@com_github_jetbrains_kotlin//:parcelize-runtime"],
            )
        }

        onContext<KtAndroidLibraryContext>(AndroidLibraryModule.ID_BUILD_TARGET_CALL) {
            deps = list["//third_party:parcelize"]
        }
    }
}
