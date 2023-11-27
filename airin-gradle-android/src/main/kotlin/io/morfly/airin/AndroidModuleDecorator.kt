/*
 * Copyright 2023 Pavlo Stavytskyi
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

package io.morfly.airin

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.BaseExtension
import groovy.xml.XmlSlurper
import groovy.xml.slurpersupport.NodeChild
import org.gradle.api.Project
import java.io.File

private val xmlParser = XmlSlurper()

open class AndroidModuleDecorator : GradleModuleDecorator {

    override fun GradleModule.decorate(project: Project) {
        with(project.plugins) {
            if (!hasPlugin("com.android.application") && !hasPlugin("com.android.library")) {
                return
            }
        }

        androidMetadata = AndroidMetadata(
            applicationId = project.applicationId,
            packageName = project.namespace ?: project.manifestPackageName,
            composeEnabled = project.composeEnabled,
            minSdkVersion = project.minSdkVersion,
            compileSdkVersion = project.compileSdkVersion,
            targetSdkVersion = project.targetSdkVersion,
            versionCode = project.versionCode,
            versionName = project.versionName
        )
    }
}

val Project.namespace: String?
    get() = extensions.findByType(CommonExtension::class.java)?.namespace

val Project.composeEnabled: Boolean
    get() = extensions.findByType(CommonExtension::class.java)?.buildFeatures?.compose ?: false

val Project.applicationId: String?
    get() = extensions.findByType(ApplicationExtension::class.java)?.defaultConfig?.applicationId

val Project.minSdkVersion: Int?
    get() = extensions.findByType(CommonExtension::class.java)?.defaultConfig?.minSdk

val Project.targetSdkVersion: Int?
    get() = extensions.findByType(ApplicationExtension::class.java)?.defaultConfig?.targetSdk

val Project.compileSdkVersion: Int?
    get() = extensions.findByType(CommonExtension::class.java)?.compileSdk

val Project.versionCode: Int?
    get() = extensions.findByType(ApplicationExtension::class.java)?.defaultConfig?.versionCode

val Project.versionName: String?
    get() = extensions.findByType(ApplicationExtension::class.java)?.defaultConfig?.versionName

val Project.androidManifestFile: File?
    get() = extensions.findByType(BaseExtension::class.java)
        ?.sourceSets
        ?.map { it.manifest.srcFile }
        ?.firstOrNull(File::exists)

val Project.manifestPackageName: String?
    get() = androidManifestFile
        ?.let(xmlParser::parse)
        ?.list()
        ?.filterIsInstance<NodeChild>()
        ?.firstOrNull { it.name() == "manifest" }
        ?.attributes()?.get("package")?.toString()
