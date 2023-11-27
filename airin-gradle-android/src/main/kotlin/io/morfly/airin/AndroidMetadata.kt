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

import java.io.Serializable

data class AndroidMetadata(
    val applicationId: String?,
    val packageName: String?,
    val composeEnabled: Boolean,
    val minSdkVersion: Int?,
    val compileSdkVersion: Int?,
    val targetSdkVersion: Int?,
    val versionCode: Int?,
    val versionName: String?
): Serializable {

    companion object {
        const val ID = "androidMetadata"
    }
}

var GradleModule.androidMetadata: AndroidMetadata?
    get() = properties[AndroidMetadata.ID] as? AndroidMetadata
    set(value) {
        if (value != null) {
            properties[AndroidMetadata.ID] = value
        } else {
            properties.remove(AndroidMetadata.ID)
        }
    }
