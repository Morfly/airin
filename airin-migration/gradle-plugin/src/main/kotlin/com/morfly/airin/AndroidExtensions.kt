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

package com.morfly.airin

import com.android.build.gradle.BaseExtension
import groovy.util.XmlSlurper
import org.gradle.api.Project
import java.io.File


private val packageNamesCache = mutableMapOf<String, String>()
private val xmlParser = XmlSlurper()

/**
 *
 */
fun Project.findPackageName(): String? {
    packageNamesCache[path]?.let { return it }

    val ext = extensions.findByType(BaseExtension::class.java)
    return ext?.defaultConfig?.applicationId
        ?: findManifest()
            ?.let(xmlParser::parse)
            ?.getProperty("@package")
            ?.toString()
            ?.also { packageNamesCache[path] = it }
}

/**
 *
 */
fun Project.findManifest(): File? {
    if (!isAndroidModule()) return null

    return extensions
        .findByType(BaseExtension::class.java)
        ?.sourceSets
        ?.map { it.manifest.srcFile }
        ?.firstOrNull(File::exists)
}

/**
 *
 */
fun Project.isAndroidModule(): Boolean =
    plugins.hasPlugin(ANDROID_LIBRARY) || plugins.hasPlugin(ANDROID_APPLICATION)