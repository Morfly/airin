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
To facilitate the migration of Android apps to Bazel, Airin provides a Gradle plugin that upon configuration, 
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
A few things are happening in the script above:
- An`app` module and all its dependencies will be migrated to Bazel.
- Modules classified as Android library, JVM library and root module will be migrated to Bazel.
- If any Android library module optionally uses technologies like Jetpack Compose, Hilt or Parcelize, they will be reflected in Bazel too.

Continue reading the documentation to find more details about components and plugin configuration.

### Migration
Finally, after the plugin is configured, the migration to Bazel is triggered with a corresponding Gradle task.
```shell
./gradlew app:migrateToBazel --no-configure-on-demand
```

> Airin plugin needs to analyze the project dependency graph during the configuration phase. 
> Therefore, [configuration on demand](https://docs.gradle.org/current/userguide/multi_project_configuration_and_execution.html#sec:configuration_on_demand) must be disabled when running the migration. 

## Gradle plugin

### Configuration options
To configure Airin Gradle plugin use `airin` extension in the root `build.gradle.kts` file.

- `targets` - configure migration targets. A `migrateToBazel` task as assigned to each migration target and triggers the migration for all its dependencies as well as the root module of the project. E.g. `targets += setOf("app")`.
- `skippedProjects` - ignore these Gradle projects during the Bazel migration. E.g. `skippedProjects += setOf(":payments-feature")`.
- `configurations` - specify allowed Gradle dependency configurations during the migration. All the rest dependencies will be ignored in Bazel. E.g. `configurations += setOf("implementation", "api")`
- `register` - register a module component that targets a specific type of modules.
- `include` - include a feature component in a module component that targets specific build features included in the module. Must be applied under the specific module component. 
- `onComponentConflict` - configure the behavior when Airin finds more then one module component that can migrate a module.
  - `Fail` - fail the build.
  - `UsePriority` - pick one with higher priority.
  - `Ignore` - ignore the module.
- `onMissingComponent` - configure the behavior when Airin can't find any module component to migrate a module.
  - `Fail` - fail the build.
  - `Ignore` - ignore the module.

### Gradle tasks

### Components

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
