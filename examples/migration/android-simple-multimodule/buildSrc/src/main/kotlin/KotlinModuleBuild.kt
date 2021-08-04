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
import template.kotlin_module_build_template


/**
 *
 */
class KotlinModuleBuild : GradlePerModuleTemplateProvider() {

    override fun canProvide(target: Project): Boolean = with(target.plugins) {
        hasPlugin(KOTLIN_JVM) && !hasPlugin(APPLICATION) && !hasPlugin(ANDROID_LIBRARY)
    }

    override fun provide(target: Project, relativePath: String): List<StarlarkFile> {
        val moduleDepsLabels = target
            .moduleDependencies("implementation", "api")
            .bazelLabels()

        val artifactDepsLabels = target
            .artifactDependencies("implementation", "api")
            .filter { it !in sharedData.ignoredArtifacts }
            .shortLabels()

        return listOf(
            kotlin_module_build_template(
                name = target.name,
                moduleDependencies = moduleDepsLabels,
                artifactDependencies = artifactDepsLabels
            )
        )
    }
}