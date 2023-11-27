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

import io.morfly.airin.FeatureComponent
import io.morfly.airin.FeatureContext
import io.morfly.airin.GradleModule
import io.morfly.airin.label.MavenCoordinates
import org.gradle.api.Project

abstract class ArtifactMappingFeature : FeatureComponent() {

    override fun canProcess(project: Project): Boolean = true

    override fun FeatureContext.onInvoke(module: GradleModule) {
        onConfiguration("implementation") {
            overrideWith("deps")
        }

        onConfiguration("api") {
            overrideWith("exports")
        }

        onConfiguration("kapt") {
            overrideWith("plugins")
        }

        onConfiguration("ksp") {
            overrideWith("plugins")
        }

        onDependency(MavenCoordinates("org.jetbrains.kotlin", "kotlin-bom")) {}

        onDependency(MavenCoordinates("org.jetbrains.kotlin", "kotlin-stdlib-jdk8")) {}

        onDependency(MavenCoordinates("androidx.compose", "compose-bom")) {}
    }
}