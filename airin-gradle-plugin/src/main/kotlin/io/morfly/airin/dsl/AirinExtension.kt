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

import io.morfly.airin.Component
import io.morfly.airin.ComponentConflictResolution.UsePriority
import io.morfly.airin.ComponentId
import io.morfly.airin.GradleModule
import io.morfly.airin.GradleModuleDecorator
import io.morfly.airin.HasId
import io.morfly.airin.MissingComponentResolution.Ignore
import io.morfly.airin.PropertiesHolder
import io.morfly.airin.property

abstract class AirinExtension :
    HasId,
    PackageComponentsHolder,
    FeatureComponentsHolder,
    PropertiesHolder,
    AirinProperties {

    override val id: String = "Airin"

    override val subcomponents: MutableMap<ComponentId, Component<GradleModule>> = linkedMapOf()

    override val properties: MutableMap<String, Any> = mutableMapOf()

    override var enabled by property(default = true)
    override var projectDecorator by property<Class<out GradleModuleDecorator>?>(default = null)
    override var targets by property(default = mutableSetOf<String>())
    override var skippedProjects by property(default = mutableSetOf<String>())
    override var configurations by property(default = defaultConfigurations)
    override var skippedConfigurations by property(default = mutableSetOf<String>())
    override var onComponentConflict by property(default = UsePriority)
    override var onMissingComponent by property(default = Ignore)

    /**
     * Specify a custom [GradleModuleDecorator].
     * Allows adding additional data to [GradleModule] instances.
     */
    fun <D : GradleModuleDecorator> decorateWith(type: Class<D>) {
        projectDecorator = type
    }

    companion object {
        const val NAME = "airin"
    }
}
