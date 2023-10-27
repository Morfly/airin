package io.morfly.airin.feature

import io.morfly.airin.FeatureContext
import io.morfly.airin.GradleFeatureComponent
import io.morfly.airin.GradleProject
import io.morfly.airin.module.AndroidLibraryModule
import io.morfly.airin.module.RootModule
import io.morfly.airin.plugin.AirinAndroidGradlePlugin
import io.morfly.airin.plugin.isComposeEnabled
import io.morfly.pendant.starlark.AndroidLibraryContext
import io.morfly.pendant.starlark.artifact
import io.morfly.pendant.starlark.kt_compiler_plugin
import io.morfly.pendant.starlark.lang.context.BuildContext
import io.morfly.pendant.starlark.lang.onContext
import org.gradle.api.Project

abstract class JetpackComposeFeature : GradleFeatureComponent() {

    override fun canProcess(target: Project): Boolean =
        target.isComposeEnabled || target.plugins.hasPlugin(AirinAndroidGradlePlugin.ID)

    override fun FeatureContext.onInvoke(packageDescriptor: GradleProject) {
        onContext<BuildContext>(id = RootModule.ID_THIRD_PARTY_BUILD) {
            load("@io_bazel_rules_kotlin//kotlin:core.bzl", "kt_compiler_plugin")

            kt_compiler_plugin(
                name = "jetpack_compose_compiler_plugin",
                id = "androidx.compose.compiler",
                target_embedded_compiler = True,
                visibility = list["//visibility:public"],
                deps = list[
                    artifact("androidx.compose.compiler:compiler"),
                ],
            )
        }

        onContext<AndroidLibraryContext>(AndroidLibraryModule.ID_BUILD_TARGET_CALL) {
            plugins = list["//third_party:jetpack_compose_compiler_plugin"]
        }
    }
}