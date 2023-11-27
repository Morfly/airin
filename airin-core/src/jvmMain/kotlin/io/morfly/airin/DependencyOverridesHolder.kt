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

import io.morfly.airin.label.BazelLabel
import io.morfly.airin.label.Label

typealias StringLabel = String
typealias DependencyOverrideCollection = MutableMap<StringLabel, MutableMap<ConfigurationName?, MutableList<DependencyOverride>>>

interface DependencyOverridesHolder {

    val dependencyOverrides: DependencyOverrideCollection

    fun onDependency(
        label: String,
        configuration: String? = null,
        override: DependencyOverrideContext.() -> Unit
    ) {
        require(label.isNotBlank()) { "Dependency label can't be blank!" }

        val overrides = DependencyOverrideContext().apply(override).dependencyOverrides
        dependencyOverrides
            .getOrPut(label, ::mutableMapOf)
            .getOrPut(configuration, ::mutableListOf) += overrides
    }

    fun <D : Label> onDependency(
        label: D,
        configuration: String? = null,
        override: DependencyOverrideContext.() -> Unit
    ) {
        require(label !is BazelLabel) {
            "Bazel labels are not allowed to be overridden!"
        }
        val overrides = DependencyOverrideContext().apply(override).dependencyOverrides
        val stringLabel = label.asShortLabel().toString()
        dependencyOverrides
            .getOrPut(stringLabel, ::mutableMapOf)
            .getOrPut(configuration, ::mutableListOf) += overrides
    }
}

class DependencyOverrideContext {

    val dependencyOverrides = mutableListOf<DependencyOverride>()

    fun overrideWith(label: Label, newConfiguration: String? = null) {
        dependencyOverrides += DependencyOverride(label, newConfiguration)
    }
}

data class DependencyOverride(val label: Label, val configuration: String? = null)
