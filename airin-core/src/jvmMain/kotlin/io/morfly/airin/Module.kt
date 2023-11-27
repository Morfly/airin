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

abstract class Module : PropertiesHolder {

    abstract val name: String

    abstract val isRoot: Boolean

    abstract val label: Label

    abstract val dirPath: String

    abstract val relativeDirPath: String

    abstract val skipped: Boolean

    abstract val moduleComponentId: String?

    abstract val featureComponentIds: Set<String>

    abstract val originalDependencies: Map<ConfigurationName, List<Label>>

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
