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

package org.morfly.airin

import org.morfly.airin.plugin.dsl.AirinExtension
import org.morfly.airin.starlark.elements.StarlarkFile
import org.morfly.airin.starlark.writer.StarlarkFileWriter
import org.gradle.api.Project


/**
 *
 */
class GradleMigratorToBazel(
    private val extension: AirinExtension,
    private val writer: StarlarkFileWriter
) : MigratorToBazel<Project>, SharedMigrationData {

    override val allArtifacts = mutableSetOf<MavenArtifact>()
    private val ignoredArtifacts = extension.artifacts.ignored
        .mapTo(linkedSetOf(), ::MavenArtifact)

    private lateinit var providers: GradleTemplateProvidersHolder
    private val files = mutableMapOf<String, MutableList<StarlarkFile>>()

    private fun initializeProviders(providers: Set<Class<out GradleTemplateProvider>>): GradleTemplateProvidersHolder {
        val instances = mutableListOf<GradleTemplateProvider>()
        for (provider in providers.reversed()) {
            instances += provider.getDeclaredConstructor().newInstance()
                .also { it.sharedData = this }
        }
        return GradleTemplateProvidersHolder(instances)
    }

    override fun migrate(target: Project) {
        val providerClasses = extension.templates.providers
        providers = initializeProviders(providerClasses)
        collectPerModuleFiles(target)
        collectStandaloneFiles(target)
        writeFiles(target)
    }

    private fun collectPerModuleFiles(target: Project) {
        target.allprojects.forEach {
            allArtifacts += it.findArtifactDependencies().filter { dep -> dep !in ignoredArtifacts }

            val relativePath = it.rootProject.relativePath(it.projectDir)
            if (relativePath !in files) {
                files[relativePath] = mutableListOf()
            }
            files[relativePath]!! += providers.providePerModule(it, relativePath)
        }
    }

    private fun collectStandaloneFiles(target: Project) {
        val relativePath = ""
        if (relativePath !in files) {
            files[relativePath] = mutableListOf()
        }
        files[relativePath]!! += providers.provideStandalone(target)
    }

    private fun writeFiles(target: Project) {
        val rootDir = target.projectDir.path
        files.entries.forEach { (relativePath, starlarkFiles) ->
            starlarkFiles.forEach { starlarkFile ->
                val path = rootDir + if (relativePath.isNotEmpty()) "/$relativePath" else ""
                writer.write(target.file(path), starlarkFile)
            }
        }
    }
}