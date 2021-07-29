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

@file:Suppress("SpellCheckingInspection")

import org.gradle.api.Project
import org.morfly.airin.GradleStandaloneTemplateProvider
import org.morfly.airin.starlark.elements.StarlarkFile
import org.morfly.airin.starlark.writer.FileWriter
import template.bazelrc_template
import template.root_workspace_template
import java.io.File


class Workspace : GradleStandaloneTemplateProvider() {

    override fun provide(target: Project, relativePath: String): List<StarlarkFile> {
        val rootProjectDir = target.rootDir
        createBazelRcFile(rootProjectDir.path)
        createBazelVersionFile(rootProjectDir.path)

        val (composeArtifacts, otherArtifacts) = sharedData.allArtifacts.partition {
            it.group?.startsWith("androidx.compose") ?: false
        }
        return listOf(
            root_workspace_template(
                artifactList = otherArtifacts.map { it.toString(includeVersion = true) },
                composeArtifactsWithoutVersion = composeArtifacts.map { it.toString(includeVersion = false) }
            )
        )
    }

    private fun createBazelRcFile(rootProjectDir: String) {
        val bazelRc = bazelrc_template(javaToolchainTarget = ToolsBuild.JAVA_TOOLCHAIN_TARGET)
        val path = "$rootProjectDir/.bazelrc"
        FileWriter.write(File(path), bazelRc)
    }

    private fun createBazelVersionFile(rootProjectDir: String) {
        val bazelVersion = "4.1.0"
        val path = "$rootProjectDir/.bazelversion"
        FileWriter.write(File(path), bazelVersion)
    }

    companion object {
        const val DEBUG_KEYSTORE_FILE_NAME = "debug.keystore"
    }
}