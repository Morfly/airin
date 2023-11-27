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

import java.io.Serializable

data class BazelLabel(
    val workspace: String? = null,
    val path: String,
    val target: String? = null
) : Label, Serializable {

    private val stringLabel by lazy {
        buildString {
            if (workspace != null) {
                if (!workspace.startsWith("@")) append("@")
                append(workspace)
            }
            if (!path.startsWith("//")) append("//")
            append(path)
            if (target != null) {
                if (!target.startsWith(":")) append(":")
                append(target)
            }
        }
    }

    override fun toString() = stringLabel

    override fun asShortLabel(): Label = this

    override fun asBazelLabel(): BazelLabel = this
}
