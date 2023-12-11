# Airin 🎋
Airin is a tool for the automated migration of Gradle Android projects to Bazel.

- [Overview](#overview)
- [Gradle plugin](#gradle-plugin)
- [Module components](#module-components)
- [Feature components](#feature-components)
- [Shared components](#shared-components)
- [Properties](#properties)
- [Decorators](#decorators)

## Overview
To facilitate the migration of Android apps to Bazel, Airin provides a Gradle plugin. This plugin, upon configuration, 
analyzes the Gradle project structure and replicates it with Bazel by generating the corresponding Bazel files.

### Installation
To initiate Bazel adoption, apply Airin Gradle plugin in your root `build.gradle.kts` file.
```kotlin
// root build.gradle.kts
plugins {
    id("io.morfly.airin.android") version "x.y.z"
}
```
### Configuration
Next, in the same `build.gradle.kts` file, use the `airin` extension to configure the plugin and adjust it specifically 
for your project.

```kotlin
// root build.gradle.kts
airin {
    targets += setOf(":app")

    register<AndroidLibraryModule> {
        include<JetpackComposeFeature>()
        include<HiltFeature>()
        include<ParcelizeFeature>()
    }
    register<JvmLibraryModule>()

    register<RootModule> {
        include<AndroidToolchainFeature>()
    }
}
```
Read next sections in the documentation to learn more about the available configuration options for the Airin plugin.

### Migration
Finally, after configuring the plugin, execute the corresponding Gradle task to initiate the automated migration to 
Bazel. This task will analyze the Gradle project structure and, based on the Airin plugin configuration, generate 
corresponding Bazel files within the project.
```shell
./gradlew app:migrateToBazel --no-configure-on-demand
```
## Gradle plugin
In most cases, your project will include custom Gradle logic or plugins that lack open-source Bazel alternatives. 
This necessitates the development of custom Bazel solutions for those specific cases. Once completed, these solutions 
can be incorporated into Airin, enabling it to generate the Starlark code exactly as you need.
### Configuration options

**Migration targets**.

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

## License

    Copyright 2023 Pavlo Stavytskyi.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       https://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
