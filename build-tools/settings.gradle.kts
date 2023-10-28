dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        mavenLocal()
    }
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
        create("sampleLibs") {
            from(files("../gradle/sample-libs.versions.toml"))
        }
    }
}

rootProject.name = "build-tools"

include(":conventions")
