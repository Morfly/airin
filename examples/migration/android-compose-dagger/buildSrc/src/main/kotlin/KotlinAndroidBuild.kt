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
import template.RoomInfo
import template.kotlin_android_build


class KotlinAndroidBuild : GradlePerModuleTemplateProvider() {

    override fun canProvide(target: Project): Boolean =
        with(target.plugins) {
            hasPlugin("kotlin-android")
                    && (hasPlugin(ANDROID_LIBRARY) || hasPlugin(ANDROID_APPLICATION))
        }


    override fun provide(target: Project, relativePath: String): List<StarlarkFile> {
        val implArtifacts = target.artifactDependencies(setOf("implementation"))
        val kaptArtifacts = target.artifactDependencies(setOf("kapt"))
        val implModules = target.moduleDependencies(setOf("implementation"))
        val apiModules = target.moduleDependencies(setOf("api"))

        val hasRoom = implArtifacts.any { it.name.startsWith("room") }
        val hasDagger = kaptArtifacts.any { it.name.startsWith("dagger") }

        val manifest = target.relativePath(target.manifest!!)

        val formattedExternalDeps = implArtifacts.asSequence()
            .filter { it !in sharedData.ignoredArtifacts }
            .filter { !it.name.startsWith("room") }
            .map { it.toString(includeVersion = false) }
            .toList()
        val formattedExports = apiModules.map { it.bazelLabel() }
        val formattedInternalDeps = mutableSetOf<String>().also { deps ->
            deps += implModules.map { it.bazelLabel() }
            deps += formattedExports
        }

        return listOf(
            kotlin_android_build(
                targetName = target.name,
                packageName = target.packageName!!,
                hasBinary = target.plugins.hasPlugin(ANDROID_APPLICATION),
                hasCompose = target.isComposeEnabled,
                composePluginTarget = Tools.COMPOSE_PLUGIN_TARGET,
                manifestLocation = manifest,
                internalDeps = formattedInternalDeps,
                exportedTargets = formattedExports,
                externalDeps = formattedExternalDeps,
                kotlinReflectTarget = Artifacts.KOTLIN_REFLECT_TARGET,
                hasDagger = hasDagger,
                roomDeps = if (hasRoom) RoomInfo(
                    roomCompilerLibraryTaget = Tools.ROOM_PLUGIN_LIBRARY_TARGET,
                    roomRuntimeTarget = Artifacts.ROOM_RUNTIME_TARGET,
                    roomKtxTarget = Artifacts.ROOM_KTX_TARGET,
                ) else null,
                debugKeystoreFile = Workspace.DEBUG_KEYSTORE_FILE_NAME
            )
        )
    }
}