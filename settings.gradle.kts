pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    includeBuild("build-tools")
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        mavenLocal()
    }
}
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

rootProject.name = "airin"

include(
    "airin-core",
    "airin-gradle-plugin",
    "airin-gradle-android"
)