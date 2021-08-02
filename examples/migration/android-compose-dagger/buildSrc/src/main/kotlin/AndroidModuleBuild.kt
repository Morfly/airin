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

    override fun canProvide(target: Project): Boolean =
        with(target.plugins) {
            hasPlugin("kotlin-android")
                    && (hasPlugin(ANDROID_LIBRARY) || hasPlugin(ANDROID_APPLICATION))
        }


    override fun provide(target: Project, relativePath: String): List<StarlarkFile> {
        val implArtifacts = target.artifactDependencies("implementation")
        val kaptArtifacts = target.artifactDependencies("kapt")
        val implModules = target.moduleDependencies("implementation")
        val apiModules = target.moduleDependencies("api")

        val hasRoom = implArtifacts.any { it.group == "androidx.room" }
        val hasDagger = kaptArtifacts.any { it.group == "com.google.dagger" }

        val manifest = target.relativePath(target.manifest!!)

        val artifactDepsLabels = implArtifacts
            .filter { it !in sharedData.ignoredArtifacts }
            .shortLabels()

        val moduleExportsLabels = apiModules.bazelLabels()
        val moduleDepsLabels = mutableSetOf<String>().also { deps ->
            deps += implModules.bazelLabels()
            deps += moduleExportsLabels
        }

        return listOf(
            android_module_build_template(
                targetName = target.name,
                packageName = target.packageName ?: "ERROR",
                hasBinary = target.plugins.hasPlugin(ANDROID_APPLICATION),
                hasCompose = target.isComposeEnabled,
                manifestLocation = manifest,
                internalDeps = moduleDepsLabels,
                exportedTargets = moduleExportsLabels,
                externalDeps = artifactDepsLabels,
                hasDagger = hasDagger,
                hasRoom = hasRoom,
                isPublic = target.name != "impl",
                injectorModule = "app"
            )
        )
    }
}