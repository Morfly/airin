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
import io.morfly.airin.label.BazelLabel
import io.morfly.airin.label.MavenCoordinates
import io.morfly.airin.module.RootModule
import io.morfly.airin.property
import io.morfly.pendant.starlark.MavenInstallContext
import io.morfly.pendant.starlark.element.ListReference
import io.morfly.pendant.starlark.hilt_android_rules
import io.morfly.pendant.starlark.http_archive
import io.morfly.pendant.starlark.lang.context.BuildContext
import io.morfly.pendant.starlark.lang.context.WorkspaceContext
import io.morfly.pendant.starlark.lang.onContext
import io.morfly.pendant.starlark.lang.type.StringType
import org.gradle.api.Project

abstract class HiltFeature : FeatureComponent() {

    init {
        shared = true
    }

    val daggerVersion by property("2.48")
    val daggerSha by property("a796141af307e2b3a48b64a81ee163d96ffbfb41a71f0ea9cf8d26f930c80ca6")

    override fun canProcess(project: Project): Boolean = with(project.plugins) {
        hasPlugin("com.google.dagger.hilt.android") || hasPlugin("io.morfly.airin.android")
    }

    override fun FeatureContext.onInvoke(module: GradleModule) {

        onContext<WorkspaceContext>(
            id = RootModule.ID_WORKSPACE,
            checkpoint = AndroidToolsFeature.CHECKPOINT_BEFORE_JVM_EXTERNAL
        ) {
            val DAGGER_VERSION by daggerVersion
            val DAGGER_SHA by daggerSha

            http_archive(
                name = "dagger",
                sha256 = DAGGER_SHA,
                strip_prefix = "dagger-use-generated-class-instead-of-superclass",
                urls = list["https://github.com/pswaminathan/dagger/archive/refs/heads/use-generated-class-instead-of-superclass.zip"],
            )

            load("@dagger//:repositories.bzl", "dagger_repositories")
            "dagger_repositories"()

            load("@dagger//:workspace_defs.bzl", "dagger_workspace")
            "dagger_workspace"()

            load("@dagger//:workspace_defs_2.bzl", "dagger_workspace_2")
            "dagger_workspace_2"()

            load(
                "@dagger//:workspace_defs.bzl",
                "HILT_ANDROID_ARTIFACTS",
                "HILT_ANDROID_REPOSITORIES"
            )
        }

        onContext<BuildContext>(id = RootModule.ID_BUILD) {
            load("@dagger//:workspace_defs.bzl", "hilt_android_rules")

            hilt_android_rules()
        }

        onContext<MavenInstallContext>(id = AndroidToolsFeature.ID_MAVEN_INSTALL) {
            artifacts = ListReference<StringType>("HILT_ANDROID_ARTIFACTS")
            repositories = ListReference<StringType>("HILT_ANDROID_REPOSITORIES")
        }

        onDependency(MavenCoordinates("com.google.dagger", "hilt-android")) {
            overrideWith(BazelLabel(path = "", target = "hilt-android"), "deps")
        }

        onDependency(MavenCoordinates("com.google.dagger", "hilt-android-compiler")) {
            overrideWith(BazelLabel(path = "", target = "hilt-android"), "deps")
        }
    }
}