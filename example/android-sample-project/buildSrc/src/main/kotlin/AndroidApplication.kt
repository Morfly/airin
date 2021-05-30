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

import com.morfly.airin.*
import com.morlfy.airin.starlark.elements.StarlarkFile
import org.gradle.api.Project
import template.android_application_build


/**
 *
 */
class AndroidApplication : GradlePerModuleTemplateProvider() {

    override fun provide(target: Project, relativePath: String): List<StarlarkFile> {
        val moduleDependencies = mutableListOf<String>()
        val artifactDependencies = mutableListOf<String>()
        target.findDependencies().forEach {
            when (it) {
                is MavenArtifact -> artifactDependencies += it.toString(includeVersion = false)
                is ProjectModule -> moduleDependencies += it.bazelLabel()!!
            }
        }
        val file = android_application_build(
            targetName = target.name,
            packageName = target.findPackageName() ?: "not_found",
            moduleDependencies, artifactDependencies
        )
        return listOf(file)
    }

    override fun canProvide(target: Project): Boolean =
        target.plugins.hasPlugin(ANDROID_APPLICATION)
}