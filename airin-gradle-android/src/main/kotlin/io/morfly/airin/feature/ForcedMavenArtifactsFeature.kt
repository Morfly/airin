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
import io.morfly.pendant.starlark.element.ListReference
import io.morfly.pendant.starlark.lang.context.BzlContext
import io.morfly.pendant.starlark.lang.context.WorkspaceContext
import io.morfly.pendant.starlark.lang.onContext
import io.morfly.pendant.starlark.lang.type.StringType
import org.gradle.api.Project

abstract class ForcedMavenArtifactsFeature : FeatureComponent() {

    var artifacts by property(emptyList<String>())

    override fun canProcess(project: Project) = true

    override fun FeatureContext.onInvoke(module: GradleModule) {
        if (artifacts.isEmpty()) return

        onContext<BzlContext>(id = RootModule.ID_MAVEN_DEPENDENCIES_BZL) {

            val FORCED_MAVEN_ARTIFACTS by artifacts
        }

        onContext<WorkspaceContext>(
            id = RootModule.ID_WORKSPACE,
            checkpoint = AndroidToolchainFeature.CHECKPOINT_BEFORE_JVM_EXTERNAL
        ) {
            load("//third_party:maven_dependencies.bzl", "FORCED_MAVEN_ARTIFACTS")
        }

        onContext<MavenInstallContext>(id = AndroidToolchainFeature.ID_MAVEN_INSTALL) {
            artifacts = ListReference<StringType>("FORCED_MAVEN_ARTIFACTS")
        }
    }
}