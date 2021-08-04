# Android Multimodule Sample

A multimodule Android project that allows to demonstrate the migration from Gradle to Bazel using Airin.

<div>
  <img align="center" src="app.png" alt="App screenshot" height="540">
</div>

## How to build?

#### Step `1`
Use `Android Studio` to build the application with Gradle or use:
```shell
./gradlew app:assembleDebug
```
#### Step `2`
Migrate the app to Bazel and generate corresponding build scripts:
```shell
./gradlew migrateToBazel
```
#### Step `3`
Build and install the application with Bazel:
```shell
bazelisk mobile-install //app:bin
```

## Where to start?
#### Step `1`
Check the `buildSrc` directory that contains all the migration logic and Starlark templates.
#### Step `2`
Check the root `build.gradle.kts` that contains migration configuration.
