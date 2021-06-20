# Airin Examples

## Migrating to Bazel

- [android simple multimodule](migration/android-simple-multimodule): Example with the simple multi-module Android
  application migrated from Gradle to Bazel.
- [android compose dagger](migration/android-compose-dagger): Example with the multi-module Android application migrated
  from Gradle to Bazel. The project includes Jetpack Compose, Dagger 2, Room and other libraries.

## Generating Bazel projects

- [android databinding](generation/android-databinding): Example of generating an Android project that uses Kotlin
  language, Data Binding and AndroidX libraries.

#### Project structure

Each generation example consists of 2 directories:

- `config` - contains the configuration for the project to be generated. Entry point is `Main.kt` file.
- `generated-project` - contains generated Bazel project.