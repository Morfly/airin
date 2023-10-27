pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        mavenLocal()
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
    versionCatalogs {
        val sampleLibs by creating {
            from(files("gradle/sample-libs.versions.toml"))
        }
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

include(
    "samples:simple-android",
    "samples:simple-android:app"
)