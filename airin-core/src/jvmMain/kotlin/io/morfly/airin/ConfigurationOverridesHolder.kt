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

data class ConfigurationOverride(val configuration: String)

typealias ConfigurationName = String
typealias ConfigurationOverrideCollection = MutableMap<ConfigurationName, MutableList<ConfigurationOverride>>

interface ConfigurationOverridesHolder {

    val configurationOverrides: ConfigurationOverrideCollection

    /**
     * Overrides the configuration name for all dependencies under the [configuration].
     * If the [override] block is left empty, all the dependencies that belong to the
     * [configuration] will be ignored.
     *
     * Gradle's configuration names are translated to argument names passed in corresponding
     * Starlark rules in Bazel.
     */
    fun onConfiguration(
        configuration: String,
        override: ConfigurationOverrideContext.() -> Unit
    ) {
        val overrides = ConfigurationOverrideContext().apply(override).configurationOverrides
        configurationOverrides.getOrPut(configuration, ::mutableListOf) += overrides
    }
}

class ConfigurationOverrideContext {

    val configurationOverrides: MutableList<ConfigurationOverride> = mutableListOf()

    /**
     * Assigns a new configuration name for dependencies.
     */
    fun overrideWith(newConfiguration: String) {
        configurationOverrides += ConfigurationOverride(newConfiguration)
    }
}
