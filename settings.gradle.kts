enableFeaturePreview("VERSION_CATALOGS")

pluginManagement {
    // plugin versions are declared in gradle.properties
    val kotlinVersion: String by settings
    val dokkaVersion: String by settings
    val kspVersion: String by settings
    plugins {
        kotlin("jvm") version kotlinVersion
        id("org.jetbrains.dokka") version dokkaVersion
        id("com.google.devtools.ksp") version kspVersion
    }
    repositories {
        gradlePluginPortal()
        google()
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
    ":airin-library-generator",
    ":airin-starlark-stdlib"
)

include(
    ":airin-migration-core",
    ":airin-gradle",
    ":airin-gradle-android"
)