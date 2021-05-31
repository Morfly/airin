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

import org.morfly.airin.*
import org.morfly.airin.starlark.elements.StarlarkFile
import org.gradle.api.Project
import template.kotlin_library_build


/**
 *
 */
class KotlinLibrary : GradlePerModuleTemplateProvider() {

    override fun provide(target: Project, relativePath: String): List<StarlarkFile> {
        val moduleDependencies = mutableListOf<String>()
        val artifactDependencies = mutableListOf<String>()
        target.findDependencies().forEach {
            when (it) {
                is MavenArtifact -> artifactDependencies += it.toString()
                is ProjectModule -> moduleDependencies += it.toString()
            }
        }
        return listOf(
            kotlin_library_build(targetName = target.name, moduleDependencies, artifactDependencies)
        )
    }

    override fun canProvide(target: Project): Boolean =
        with(target.plugins) {
            hasPlugin(KOTLIN_JVM) && !hasPlugin(APPLICATION)
        }
}