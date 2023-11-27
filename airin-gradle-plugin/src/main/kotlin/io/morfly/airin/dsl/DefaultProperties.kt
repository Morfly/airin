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
import io.morfly.airin.GradleModuleDecorator
import io.morfly.airin.MissingComponentResolution

interface AirinProperties {
    var enabled: Boolean
    var projectDecorator: Class<out GradleModuleDecorator>?
    var targets: MutableSet<String>
    var skippedProjects: MutableSet<String>
    var configurations: MutableSet<ConfigurationName>
    var skippedConfigurations: MutableSet<ConfigurationName>
    var onComponentConflict: ComponentConflictResolution
    var onMissingComponent: MissingComponentResolution
}

interface PackageComponentProperties {
    var ignored: Boolean
    var shared: Boolean
    var priority: Int
}

interface FeatureComponentProperties {
    var ignored: Boolean
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
