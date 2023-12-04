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

import java.io.Serializable

/**
 * A resolution strategy in case no component found for a Gradle module.
 */
enum class MissingComponentResolution : Serializable {
    /**
     * Fail the build if no component found for the module.
     */
    Fail,

    /**
     * Ignore the module if no component found for it.
     */
    Ignore
}

/**
 * A resolution strategy in case more than one module component can be applied to a module.
 */
enum class ComponentConflictResolution : Serializable {
    /**
     * Fail the build in case of a conflict.
     */
    Fail,

    /**
     * Pick a component with the highest [ModuleComponent.priority].
     */
    UsePriority,

    /**
     * Ignore a module in case of a conflict.
     */
    Ignore
}
