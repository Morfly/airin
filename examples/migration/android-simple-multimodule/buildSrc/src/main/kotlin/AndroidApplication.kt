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
import template.android_application_build


/**
 *
 */
class AndroidApplication : GradlePerModuleTemplateProvider() {

    override fun provide(target: Project, relativePath: String): List<StarlarkFile> {
        val gradleConfigs = setOf("implementation", "api")

        val moduleDeps = target.moduleDependencies(gradleConfigs)
            .map { it.bazelLabel() }

        val artifactDeps = target.artifactDependencies(gradleConfigs)
            .filter { it !in sharedData.ignoredArtifacts }
            .map { it.toString(includeVersion = false) }

        return listOf(
            android_application_build(
                targetName = target.name,
                packageName = target.packageName ?: "@not_found",
                moduleDependencies = moduleDeps,
                artifactDependencies = artifactDeps
            )
        )
    }

    override fun canProvide(target: Project): Boolean =
        target.plugins.hasPlugin(ANDROID_APPLICATION)
}