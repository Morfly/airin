# Airin 🎋
Airin is a tool for the automated migration of Gradle Android projects to Bazel.

- [Overview](#overview)
- [Gradle plugin](#gradle-plugin)
- [Module components](#module-components)
- [Feature components](#feature-components)
- [Shared components](#shared-components)
- [Properties](#properties)
- [Decorators](#decorators)

## Installation

## Overview
### Installation
```kotlin
// root build.gradle.kts
plugins {
    id("io.morfly.airin.android") version "x.y.z"
}
```
### Configuration
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
### Migration
```shell
./gradlew app:migrateToBazel --no-configure-on-demand
```
## Gradle plugin
### Configuration options
**Targets**.

**Resolution strategies**.

**Registering module components**.

**Including feature components**.

**Decorating modules**.
### Gradle tasks

## Module components
### Generating Bazel files
### Dependencies
## Feature components
### Dependency overrides
### Configuration overrides
### Bazel file modifiers
## Shared components
## Properties
### Shared properties
## Decorators

