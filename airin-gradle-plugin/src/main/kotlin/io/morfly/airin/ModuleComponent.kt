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

import io.morfly.airin.dsl.FeatureComponentsHolder
import io.morfly.airin.dsl.ModuleComponentProperties
import org.gradle.api.Project
import java.io.Serializable

/**
 * The main responsibility of a module component is to generate Bazel files for a certain type of
 * project modules.
 */
abstract class ModuleComponent : AbstractModuleComponent<GradleModule>(),
    Serializable,
    FeatureComponentsHolder,
    ModuleComponentProperties {

    final override var ignored by property(default = false)
    final override var shared by property(default = false)
    final override var priority by property(default = 1)

    override val id: String = javaClass.simpleName

    /**
     * Contains the logic that defines if this component could be applied to a given Gradle module.
     */
    abstract fun canProcess(project: Project): Boolean
}
