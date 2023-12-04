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

interface Label {

    /**
     * String representation of a label.
     */
    override fun toString(): String

    /**
     * Provides a short version of the label.
     */
    fun asShortLabel(): Label

    /**
     * Converts a label to [BazelLabel].
     */
    fun asBazelLabel(): BazelLabel
}
