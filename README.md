# Airin
[![Maven Central](https://img.shields.io/maven-central/v/org.morfly.airin/airin-starlark.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22org.morfly.airin%22%20AND%20a:%22airin-starlark%22)

Airin is a tool for migrating Gradle projects to Bazel and generating Bazel build scripts.
- **Starlark Template Engine**: Airin provides a declarative, typesafe template engine for generating Starlark code. Define
  templates for your Bazel configuration files in Kotlin DSL which closely resembles Starlark itself. Check
  [the documentation](docs/airin_starlark_template_engine.md) to learn more.
- **Automated Migration to Bazel**: Airin provides a framework for migrating Gradle projects to Bazel. Define the set of
  Bazel file templates for your project and configure Airin Gradle plugin to bring automation to your migration process.
  Check [the documentation](docs/airin_gradle_migration.md) to learn more.

## How it works?

#### Step `1`

In `buildSrc` directory define a set of [Starlark templates](docs/airin_starlark_template_engine.md) for your project.

```kotlin
fun java_build(
    targetName: String,
    srcRoot: String,
    mainClass: String
    /**
     *
     */
) = BUILD.bazel {
    load("@rules_java//java:defs.bzl", "java_binary")

    java_binary(
        name = targetName,
        srcs = glob("$srcRoot/**/*.java"),
        main_class = mainClass,
        deps = list["//library"]
    )
}
```
#### Step `2`

Also, in `buildSrc` implement [`TemplateProvider`](docs/airin_gradle_migration.md)'s for each type of your Gradle modules to correctly map the right templates to the right modules.

#### Step `3`

In root `build.gradle` file configure Airin Gradle plugin by registering your newly created template providers for each type of modules or usecases.

<details open>
<summary>Kotlin</summary>

```kotlin
airin {
  
  templates {
    
    register<Workspace>()
    register<JavaLibrary>()
    register<AndroidApplication>()
  }
}
```
</details>

<details>
<summary>Groovy</summary>

```groovy
airin {
  
  templates {
    
    register Workspace
    register JavaLibrary
    register AndroidApplication
  }
}
```
</details>

In the example above `Workspace`, `JavaLibrary` and `AndroidApplication` are Starlark code template providers that implement `GradleTemplateProvider` interface.
  
#### Step `4`

Run the migration.

```shell
./gradlew migrateToBazel
```
<br>

Use [the documentation](docs/airin_gradle_migration.md) to learn more about the migration process.

## Installation

Current version: [![Maven Central](https://img.shields.io/maven-central/v/org.morfly.airin/airin-starlark.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22org.morfly.airin%22%20AND%20a:%22airin-starlark%22)
    
  
### Migration from Gradle
  
In the `buildSrc/build.gradle` file add the following:
```groovy
dependencies {
    implementation "org.morfly.airin:airin-gradle:x.y.z"
  
    // optional - with Android specific extensions
    implementation "org.morfly.airin:airin-gradle-android:x.y.z"
}
```
Then, in the root `build.gradle` file apply Airin Gradle plugin:
  
```groovy
plugins {
    id "org.morfly.airin"
}
```
  
  
### Standalone Template Engine
In case you need only Starlark code generator:
```groovy
dependencies {
    implementation "org.morfly.airin:airin-starlark:x.y.z"
}
```
<br>
  
Now you are ready to [configure the plugin](docs/airin_gradle_migration.md).

## Examples

Learn more about Airin by checking [example projects](examples).

## License

```
Copyright 2021 Pavlo Stavytskyi

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
