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

import io.morfly.airin.label.Label

/**
 * A module representation that holds all the information about it.
 */
abstract class Module : PropertiesHolder {

    /**
     * Module name.
     */
    abstract val name: String

    /**
     * Checks if it is a root module.
     */
    abstract val isRoot: Boolean

    /**
     * Module label.
     */
    abstract val label: Label

    /**
     * Module location in the file system.
     */
    abstract val dirPath: String

    /**
     * Module location in relation to the project directory.
     */
    abstract val relativeDirPath: String

    /**
     * Checks if the module is skipped during the migration to Bazel.
     */
    abstract val skipped: Boolean

    /**
     * Id of a selected module component. Module is [skipped] if it is null.
     */
    abstract val moduleComponentId: String?

    /**
     * Set of selected feature components.
     */
    abstract val featureComponentIds: Set<String>

    /**
     * Dependencies as per the configuration in the original build system.
     */
    abstract val originalDependencies: Map<ConfigurationName, List<Label>>

    /**
     * Mapped dependencies to be declared in the Bazel target.
     */
    abstract var dependencies: Map<ConfigurationName, List<Label>>
        internal set
}

fun Module.transformDependencies(features: List<FeatureContext>): Map<ConfigurationName, List<Label>> {
    val transformedDependencies = mutableMapOf<ConfigurationName, MutableSet<Label>>()
    val processedDependencies = mutableMapOf<ConfigurationName, MutableSet<Label>>()

    fun append(dependencies: Map<ConfigurationName, Set<Label>>) {
        for ((config, labels) in dependencies)
            transformedDependencies.getOrPut(config, ::mutableSetOf) += labels
    }

    DependencyOverrideProcessor(features, processedDependencies)
        .invoke(this)
        .also(::append)
    ConfigurationOverrideProcessor(features, processedDependencies)
        .invoke(this)
        .also(::append)
    AddedDependencyProcessor(features)
        .invoke(this)
        .also(::append)

    return transformedDependencies.mapValues { (_, dependencies) ->
        dependencies.sortedBy { it.asBazelLabel().toString() }
    }
}
