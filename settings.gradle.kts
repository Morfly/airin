enableFeaturePreview("VERSION_CATALOGS")

pluginManagement {
    // plugin versions are declared in gradle.properties
    val kotlinPluginVersion: String by settings
    val dokkaPluginVersion: String by settings
    val kspPluginVersion: String by settings
    plugins {
        kotlin("jvm") version kotlinPluginVersion
        id("org.jetbrains.dokka") version dokkaPluginVersion
        id("com.google.devtools.ksp") version kspPluginVersion
    }
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
    }

    // version catalog is declared in gradle/libs.versions.toml
    defaultLibrariesExtensionName.set("deps")
}

rootProject.name = "airin"

include(
    ":airin-starlark",
    ":airin-library-generator"
)

include(
    ":airin-migration-core",
    ":airin-gradle",
    ":airin-gradle-android"
)