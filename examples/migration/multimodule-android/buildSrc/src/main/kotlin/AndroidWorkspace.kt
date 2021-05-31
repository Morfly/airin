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

import org.morfly.airin.GradleStandaloneTemplateProvider
import org.morfly.airin.starlark.elements.StarlarkFile
import org.gradle.api.Project
import template.android_workspace


/**
 *
 */
class AndroidWorkspace : GradleStandaloneTemplateProvider() {

    override fun provide(target: Project, relativePath: String): List<StarlarkFile> {
        val file = android_workspace(
            workspaceName = "android-application",
            repositoriesList = listOf(
                "https://maven.google.com",
                "https://repo1.maven.org/maven2"
            ),
            artifactsList = data.allArtifacts.map { it.toString() }
        )
        return listOf(file)
    }
}