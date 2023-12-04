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

import io.morfly.airin.ComponentsHolder
import io.morfly.airin.ModuleComponent
import io.morfly.airin.GradleModule
import org.gradle.api.Action
import org.gradle.api.model.ObjectFactory
import javax.inject.Inject

interface PackageComponentsHolder : ComponentsHolder<GradleModule> {

    @get:Inject
    val objects: ObjectFactory

    /**
     * Register [ModuleComponent] in Airin Gradle plugin for migrating a corresponding type of modules.
     */
    fun <B : ModuleComponent> register(
        type: Class<B>,
        config: Action<B>? = null
    ) {
        val component = objects.newInstance(type)
        if (component.id in subcomponents) {
            error("Duplicate component $type found in ${this.javaClass}!")
        }
        config?.execute(component)
        subcomponents[component.id] = component
    }
}
