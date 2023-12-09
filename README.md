# Airin 🎋
Airin is a tool for the automated migration of Gradle Android projects to Bazel.

- [Installation](#installation)
- [Overview](#overview)
- [Module components](#module-components)
- [Feature components](#feature-components)
- [Shared components](#shared-components)
- [Properties](#properties)
- [Decorators](#decorators)

## Installation
```kotlin
// root build.gradle.kts
plugins {
    id("io.morfly.airin.android") version "x.y.z"
}
```
## Plugin configuration
```kotlin
// root build.gradle.kts
airin {
    targets += setOf(":app")

    register<AndroidLibraryModule> {
        include<JetpackComposeFeature>()
        include<HiltFeature>()
        include<ParcelizeFeature>()
        ...
    }
    register<JvmLibraryModule>()

    register<RootModule> {
        include<AndroidToolchainFeature>()
    }
}
```
## Module components
### Generating Bazel files
### Dependencies
## Feature components
### Dependency overrides
### Configuration overrides
### Modifying Bazel files 
## Shared components
## Properties
### Shared properties
## Decorators

