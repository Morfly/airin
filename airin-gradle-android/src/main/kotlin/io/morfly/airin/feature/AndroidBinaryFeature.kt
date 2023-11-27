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

package io.morfly.airin.feature

import io.morfly.airin.FeatureContext
import io.morfly.airin.FeatureComponent
import io.morfly.airin.GradleModule
import io.morfly.airin.androidMetadata
import io.morfly.airin.module.AndroidLibraryModule
import io.morfly.pendant.starlark.android_binary
import io.morfly.pendant.starlark.lang.context.BuildContext
import io.morfly.pendant.starlark.lang.onContext
import org.gradle.api.Project

abstract class AndroidBinaryFeature : FeatureComponent() {

    override fun canProcess(project: Project): Boolean =
        project.plugins.hasPlugin("com.android.application")

    override fun FeatureContext.onInvoke(module: GradleModule) {
        onContext<BuildContext>(AndroidLibraryModule.ID_BUILD) {
            android_binary {
                name = "${module.name}_bin"
                incremental_dexing = 1
                manifest = "src/main/AndroidManifest.xml"
                manifest_values = dict {
                    val data = module.androidMetadata

                    "applicationId" to data?.applicationId
                    "minSdkVersion" to data?.minSdkVersion?.toString()
                    "targetSdkVersion" to data?.targetSdkVersion?.toString()
                    "compileSdkVersion" to data?.compileSdkVersion?.toString()
                    "versionCode" to data?.versionCode?.toString()
                    "versionName" to data?.versionName
                }
                multidex = "native"
                deps = list[":${module.name}"]
            }
        }
    }
}