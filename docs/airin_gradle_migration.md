# Migration from Gradle to Bazel

Airin ships with a Gradle plugin that aims to automate the migration process.

With Airin you can specify various types of Bazel build scripts, and the way how they map to corresponding Gradle
modules.

For example, Gradle's `Java library` and `Android Library` would require completely different Bazel build scripts and
with Airin you would be able to easily configure each of them.

## Migration Process

### Step `1` - `buildSrc`

Create `buildSrc` directory in the root directory of your project.

Create `build.gradle` file inside and configure it with Kotlin and add `Airin` as a dependency.




```groovy
plugins {
    id "org.jetbrains.kotlin.jvm" version "1.5.10"
}

repositories {
    mavenCentral()
    google()
}

dependencies {
    implementation "org.morfly.airin:airin-gradle:0.2.0"
    
    // optional - Android specific extensions
    implementation "org.morfly.airin:airin-gradle-android:0.2.0"
}
```

[Learn more](https://docs.gradle.org/current/userguide/organizing_gradle_projects.html#sec:build_sources)
about `buildSrc` from the official Gradle documentation.

### Step `2` - Creating templates

In `buildSrc` create a `src/main/kotlin/templates` directory. It will store all of
our [Starlark templates](airin_starlark_template_engine.md).

As an example, a simple Java application template would look like this:

```kotlin
// templates/java_build.kt

fun java_library_build(
    targetName: String
    /**
     *
     */
): StarlarkFile = BUILD.bazel {
    load("@rules_java//java:defs.bzl", "java_library")

    java_library(
        name = targetName,
        srcs = glob("src/main/java/**/*.java"),
    )
}
```

### Step `3` - Creating template providers

Template provider - is an entity that maps our newly created templates to the right types of Gradle modules. In case of
Gradle each template provider has `GradleTemplateProvider` as a parent.

Each `GradleTemplateProvider` consists of 2 functions that are to be implemented by the user:

- `fun canProvide(target: Project): Boolean` - that decides whether the template provider is able to provide templates
  for this particular Gradle `Project`.
  > Note: Usually, it is enough to check if the `Project` has a specific Gradle plugin attached to it.
  > For example:  `target.plugins.hasPlugin("java-library")`
- `fun provide(target: Project, relativePath: String): List<StarlarkFile>` - provides the list of Starlark files for
  this particular Gradle `Project`.

User is able to directly inherit one of the two types of template providers:

- `GradlePerModuleTemplateProvider` - that maps Starlark templates to the specific Gradle modules. Airin will trigger
  every such provider for each of Gradle modules to define the one that can provide templates for the specific Gradle
  module.
  > Note: For each Gradle module Airin will trigger registered providers one by one until it finds the first one that can provide
  > templates. Therefore, it is **IMPORTANT** in which order template providers are registered. Learn more in [Step 4](#step-4---configuring-airing-gradle-plugin).
- `GradleStandaloneTemplateProvide` - that is being executed once per migration and define standalone Bazel scripts such
  as `WORKSPACE` etc. This type of provider is being triggered after `per module` ones.

<details open>
<summary>Example</summary>

```kotlin
class JavaLibrary : GradlePerModuleTemplateProvider() {

    override fun provide(target: Project, relativePath: String): List<StarlarkFile> = listOf(
        java_library_build(targetName = target.name)
    )

    override fun canProvide(target: Project): Boolean =
        target.plugins.hasPlugin("java-library")
}
```

</details>

### Step `4` - Configuring Airing Gradle plugin

In the root `build.gradle` file apply Airin Gradle plugin.
    
```groovy
plugins {
    id "org.morfly.airin"
}
```

After that use Airin dsl to configure it with the template providers that you've created earlier.

<details open>
<summary>Kotlin</summary>

```kotlin
airin {
    templates {
        register<JavaLibrary>() // extends GradlePerModuleTemplateProvider
        register<Workspace>() // extends GradleStandaloneTemplateProvider
        register<AndroidApplication>() // extends GradlePerModuleTemplateProvider
    }
}
```

</details>

<details>
<summary>Groovy</summary>

```groovy
airin {
    templates {
        register JavaLibrary // extends GradlePerModuleTemplateProvider
        register Workspace // extends GradleStandaloneTemplateProvider
        register AndroidApplication // extends GradlePerModuleTemplateProvider
    }
}
```

</details>

> Note: The order of registering template providers **matters**.
> Airin will trigger providers from the last registered to the first that can provide requested templates.
> In the example above the order will be the following:
> 1. AndroidApplication - as it is the last registered provider
> 2. JavaLibrary - as it is the only left `per module` provider
> 3. Workspace - as all `standalone` providers are being evaluate in the end.

### Step `5` - Run the migration

After all the template providers being registered it is time to run the migration.

```shell
./gradlew migrateToBazel
```
