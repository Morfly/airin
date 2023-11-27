/*
 * Copyright 2023 Pavlo Stavytskyi
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

package io.morfly.airin.feature

import io.morfly.airin.FeatureContext
import io.morfly.airin.FeatureComponent
import io.morfly.airin.GradleModule
import io.morfly.airin.module.AndroidLibraryModule
import io.morfly.airin.module.RootModule
import io.morfly.airin.plugin.AirinAndroidGradlePlugin
import io.morfly.airin.composeEnabled
import io.morfly.airin.module.RootModule.Companion.ID_MAVEN_ARTIFACTS
import io.morfly.pendant.starlark.KtAndroidLibraryContext
import io.morfly.pendant.starlark.artifact
import io.morfly.pendant.starlark.kt_compiler_plugin
import io.morfly.pendant.starlark.lang.context.BuildContext
import io.morfly.pendant.starlark.lang.context.ListContext
import io.morfly.pendant.starlark.lang.onContext
import io.morfly.pendant.starlark.lang.type.StringType
import org.gradle.api.Project

abstract class JetpackComposeFeature : FeatureComponent() {

    init {
        shared = true
    }

    override fun canProcess(project: Project): Boolean =
        project.composeEnabled || project.plugins.hasPlugin(AirinAndroidGradlePlugin.ID)

    override fun FeatureContext.onInvoke(module: GradleModule) {
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

        onContext<ListContext<StringType>>(id = ID_MAVEN_ARTIFACTS) {
            item("androidx.compose.compiler:compiler:1.4.7")
        }

        onContext<KtAndroidLibraryContext>(AndroidLibraryModule.ID_BUILD_TARGET_CALL) {
            plugins = list["//third_party:jetpack_compose_compiler_plugin"]
        }
    }
}