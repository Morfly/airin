/*
 * Copyright 2021 Pavlo Stavytskyi
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

@file:Suppress("RemoveExplicitTypeArguments")


val airinPublications = mapOf<GradleProjectName, Publication>(

    "airin-starlark" to Publication(
        name = "Airin Starlark",
        description = "A declarative, type-safe Starlark template engine that allows writing Starlark code templates in Kotlin."
    ),

    "airin-starlark-stdlib" to Publication(
        name = "Airin Starlark Standard Library",
        description = "A Kotlin DSL that represent common Starlark rules and functions that include Java, Android, Kotlin and more."
    ),

    "airin-starlark-libgen" to Publication(
        name = "Airin Starlark Library Generator",
        description = "A symbol processor for generating Kotlin DSL for custom Starlark rules and functions."
    ),

    "airin-migration-core" to Publication(
        name = "Airin Migration Core",
        description = "Core APIs for migration to Bazel."
    ),

    "airin-gradle" to Publication(
        name = "Airin Gradle",
        description = "Airin Gradle plugin for migration to Bazel."
    ),

    "airin-gradle-android" to Publication(
        name = "Airin Gradle Android",
        description = "Android extensions for Airin Gradle plugin."
    )
)


typealias GradleProjectName = String

data class Publication(val name: String, val description: String)