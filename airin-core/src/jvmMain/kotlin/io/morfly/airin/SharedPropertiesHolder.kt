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

import io.morfly.airin.label.MavenCoordinates

/**
 * Properties used by components during the migration process. Could be shared between the module
 * and feature components withing the migration of a single module.
 */
interface SharedPropertiesHolder {

    val sharedProperties: MutableMap<String, Any>
}

@Suppress("UNCHECKED_CAST")
val SharedPropertiesHolder.allMavenArtifacts: MutableSet<MavenCoordinates>
    get() = sharedProperties.getOrPut("allMavenArtifacts") {
        mutableSetOf<MavenCoordinates>()
    } as MutableSet<MavenCoordinates>
