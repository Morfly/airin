@file:Suppress("RemoveExplicitTypeArguments")


val airinPublications = mapOf<GradleProjectName, Publication>(

    "airin-starlark" to Publication(
        name = "Airin Starlark",
        description = "A declarative, type-safe Starlark template engine that allows writing Starlark code templates in Kotlin."
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