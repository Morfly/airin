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

import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import groovy.util.XmlSlurper
import org.gradle.api.Project
import java.io.File


private typealias PackageName = String
private typealias ManifestFilePath = String

private val packageNamesCache = mutableMapOf<String, PackageName>()
private val manifestFilesCache = mutableMapOf<String, ManifestFilePath>()
private val xmlParser = XmlSlurper()

/**
 *
 */
val Project.packageName: String?
    get() {
        if (!isAndroidModule) return null
        packageNamesCache[path]?.let { return it }

        val ext = extensions.findByType(BaseExtension::class.java)
        return ext?.defaultConfig?.applicationId
            ?: manifest
                ?.let(xmlParser::parse)
                ?.getProperty("@package")
                ?.toString()
                ?.also { packageNamesCache[path] = it }
    }

/**
 *
 */
val Project.manifest: File?
    get() {
        if (!isAndroidModule) return null
        manifestFilesCache[path]?.let { return File(it) }

        return extensions
            .findByType(BaseExtension::class.java)
            ?.sourceSets
            ?.map { it.manifest.srcFile }
            ?.firstOrNull(File::exists)
            ?.also { file -> manifestFilesCache[this.path] = file.path }
    }

/**
 *
 */
val Project.isAndroidModule: Boolean
    get() = plugins.hasPlugin(ANDROID_LIBRARY) || plugins.hasPlugin(ANDROID_APPLICATION)

/**
 *
 */
@Suppress("UnstableApiUsage")
val Project.isComposeEnabled: Boolean
    get() = extensions.findByType(CommonExtension::class.java)?.buildFeatures?.compose ?: false

/**
 *
 */
@Suppress("UnstableApiUsage")
val Project.isDataBindingEnabled: Boolean
    get() = extensions.findByType(BaseAppModuleExtension::class.java)?.buildFeatures?.dataBinding ?: false ||
            extensions.findByType(BaseExtension::class.java)?.dataBinding?.isEnabled ?: false
