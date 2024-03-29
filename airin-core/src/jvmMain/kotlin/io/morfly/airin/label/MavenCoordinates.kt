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

package io.morfly.airin.label

import io.morfly.airin.nonAlphaNumericRegex
import java.io.Serializable

/**
 * Label that represents artifact coordinates.
 *
 * A short version of the label omits the artifact version.
 */
data class MavenCoordinates(
    val group: String,
    val name: String,
    val version: String? = null
) : Label, Serializable {

    override fun toString() = buildString {
        append(group)
        append(':')
        append(name)
        if (version != null) {
            append(':')
            append(version)
        }
    }

    /**
     * Returns a shortened version of the label without the artifact version.
     */
    override fun asShortLabel(): Label =
        if (version != null) copy(version = null) else this

    /**
     * Converts the label to Bazel label as per rules_jvm_external.
     */
    override fun asBazelLabel() = BazelLabel(
        workspace = "maven",
        path = "",
        target = asShortLabel().toString().replace(nonAlphaNumericRegex, "_")
    )
}
