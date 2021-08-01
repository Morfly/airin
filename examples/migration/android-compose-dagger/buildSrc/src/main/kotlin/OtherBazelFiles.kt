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
import java.io.File


class OtherBazelFiles : GradleStandaloneTemplateProvider() {

    override fun provide(target: Project, relativePath: String): List<StarlarkFile> {
        val rootProjectDir = target.rootDir
        createBazelRcFile(rootProjectDir.path)
        createBazelVersion(rootProjectDir.path)

        return emptyList()
    }

    private fun createBazelRcFile(rootProjectDir: String) {
        val bazelRc = bazelrc_template()
        val path = "$rootProjectDir/.bazelrc"
        FileWriter.write(File(path), bazelRc)
    }

    private fun createBazelVersion(rootProjectDir: String) {
        val bazelVersion = "4.1.0"
        val path = "$rootProjectDir/.bazelversion"
        FileWriter.write(File(path), bazelVersion)
    }
}