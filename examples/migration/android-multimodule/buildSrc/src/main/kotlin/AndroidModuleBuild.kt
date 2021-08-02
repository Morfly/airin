/*
 * Copyright 2021 Pavlo Stavytskyi
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

import org.gradle.api.Project
import org.morfly.airin.*
import org.morfly.airin.starlark.elements.StarlarkFile
import template.android_module_build_template


class AndroidModuleBuild : GradlePerModuleTemplateProvider() {

    override fun canProvide(target: Project): Boolean = with(target.plugins) {
        hasPlugin("kotlin-android")
                && (hasPlugin(ANDROID_LIBRARY) || hasPlugin(ANDROID_APPLICATION))
    }

    override fun provide(target: Project, relativePath: String): List<StarlarkFile> {
        val moduleDepsLabels = mutableListOf<String>()

        if (sharedData.ignoredArtifacts.any { it.group == "com.google.dagger" })
            moduleDepsLabels += "//:dagger"

        moduleDepsLabels += target
            .moduleDependencies("implementation")
            .bazelLabels()

        val artifactDepsLabels = target
            .artifactDependencies("implementation")
            .filter { it !in sharedData.ignoredArtifacts }
            .shortLabels()

        return listOf(
            android_module_build_template(
                name = target.name,
                packageName = target.packageName ?: "ERROR",
                hasBinary = target.plugins.hasPlugin(ANDROID_APPLICATION),
                artifactDeps = artifactDepsLabels,
                moduleDeps = moduleDepsLabels
            )
        )
    }
}