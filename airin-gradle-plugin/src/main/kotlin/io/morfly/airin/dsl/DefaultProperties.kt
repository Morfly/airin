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

package io.morfly.airin.dsl

import io.morfly.airin.ComponentConflictResolution
import io.morfly.airin.ConfigurationName
import io.morfly.airin.FeatureComponent
import io.morfly.airin.GradleModule
import io.morfly.airin.GradleModuleDecorator
import io.morfly.airin.MissingComponentResolution
import io.morfly.airin.ModuleComponent

/**
 * Properties of the Airin Gradle plugin.
 */
interface AirinProperties {
    /**
     * Specify is Airin Gradle plugin should configure the migration tasks in corresponding
     * Gradle projects.
     */
    var enabled: Boolean

    /**
     * Project decorator that adds additional data to [GradleModule] instances.
     */
    var projectDecorator: Class<out GradleModuleDecorator>?

    /**
     * Set of Gradle modules tha
     */
    var targets: MutableSet<String>

    /**
     * Gradle projects that will be skipped during the migration to Bazel.
     * Expects strings that represent Gradle project path.
     * E.g. :my-feature
     */
    var skippedProjects: MutableSet<String>

    /**
     * Set of Gradle configuration names used during the migration to Bazel.
     * All the dependencies that use other configurations will be ignored in Bazel scripts.
     */
    var configurations: MutableSet<ConfigurationName>

    /**
     * Set of skipped Gradle configurations.
     */
    var skippedConfigurations: MutableSet<ConfigurationName>

    /**
     * A resolution strategy in case more than one module component can be applied to a module.
     */
    var onComponentConflict: ComponentConflictResolution

    /**
     * A resolution strategy in case no component found for a Gradle module.
     */
    var onMissingComponent: MissingComponentResolution
}

/**
 * Default properties of [ModuleComponent] instances.
 */
interface ModuleComponentProperties {
    /**
     * Ignore this module component.
     */
    var ignored: Boolean

    /**
     * Make this module component shared to allow receiving contributions from all shared feature
     * components.
     */
    var shared: Boolean

    /**
     * Priority of the component. The higher the number the higher is the priority.
     * Used by [ComponentConflictResolution.UsePriority].
     */
    var priority: Int
}

/**
 * Default properties of [FeatureComponent] instances.
 */
interface FeatureComponentProperties {
    /**
     * Ignore this feature component.
     */
    var ignored: Boolean

    /**
     * Make this feature component shared to allow it to contribute to all other shared module
     * components.
     */
    var shared: Boolean
}

val defaultConfigurations
    get() = mutableSetOf(
        "implementation",
        "debugImplementation",
        "releaseImplementation",
        "api",
        "debugApi",
        "releaseApi",
        "kapt",
        "ksp"
    )
