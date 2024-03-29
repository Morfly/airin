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
import io.morfly.airin.module.RootModule
import io.morfly.airin.property
import io.morfly.pendant.starlark.MavenInstallContext
import io.morfly.pendant.starlark.dagger_android_rules
import io.morfly.pendant.starlark.element.ListReference
import io.morfly.pendant.starlark.http_archive
import io.morfly.pendant.starlark.lang.context.BuildContext
import io.morfly.pendant.starlark.lang.context.WorkspaceContext
import io.morfly.pendant.starlark.lang.onContext
import io.morfly.pendant.starlark.lang.type.StringType
import org.gradle.api.Project

abstract class DaggerAndroidFeature : FeatureComponent() {

    val daggerVersion by property("2.47")
    val daggerSha by property("154cdfa4f6f552a9873e2b4448f7a80415cb3427c4c771a50c6a8a8b434ffd0a")

    override fun canProcess(project: Project): Boolean = true

    override fun FeatureContext.onInvoke(module: GradleModule) {

        onContext<WorkspaceContext>(
            id = RootModule.ID_WORKSPACE,
            checkpoint = AndroidToolchainFeature.CHECKPOINT_BEFORE_JVM_EXTERNAL
        ) {
            val DAGGER_VERSION by daggerVersion
            val DAGGER_SHA by daggerSha

            http_archive(
                name = "dagger",
                sha256 = DAGGER_SHA,
                strip_prefix = "dagger-dagger-%s" `%` DAGGER_VERSION,
                urls = list["https://github.com/google/dagger/archive/dagger-%s.zip" `%` DAGGER_VERSION],
            )

            load("@dagger//:workspace_defs.bzl", "DAGGER_ANDROID_ARTIFACTS", "DAGGER_ANDROID_REPOSITORIES")
        }

        onContext<BuildContext>(id = RootModule.ID_BUILD) {
            load("@dagger//:workspace_defs.bzl", "dagger_android_rules")

            dagger_android_rules()
        }

        onContext<MavenInstallContext>(id = AndroidToolchainFeature.ID_MAVEN_INSTALL) {
            artifacts = ListReference<StringType>("DAGGER_ANDROID_ARTIFACTS")
            repositories = ListReference<StringType>("DAGGER_ANDROID_REPOSITORIES")
        }
    }
}
