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

import io.morfly.airin.FeatureComponent
import io.morfly.airin.GradleModule
import io.morfly.airin.GradleModuleDecorator
import io.morfly.airin.ModuleComponent
import io.morfly.airin.dsl.AirinExtension
import io.morfly.airin.dsl.FeatureComponentsHolder
import io.morfly.airin.dsl.PackageComponentsHolder
import org.gradle.api.Action

/**
 * Register [ModuleComponent] in Airin Gradle plugin for migrating a corresponding type of modules.
 */
inline fun <reified C : ModuleComponent> PackageComponentsHolder.register(config: Action<C>? = null) =
    register(C::class.java, config)

/**
 * Include [FeatureComponent] in Airin Gradle plugin for adding a corresponding build feature to
 * files generated by a related [ModuleComponent].
 */
inline fun <reified C : FeatureComponent> FeatureComponentsHolder.include(config: Action<C>? = null) =
    include(C::class.java, config)

/**
 * Specify a custom [GradleModuleDecorator].
 * Allows adding additional data to [GradleModule] instances.
 */
inline fun <reified D : GradleModuleDecorator> AirinExtension.decorateWith() {
    decorateWith(D::class.java)
}
