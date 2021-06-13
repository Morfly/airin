# Android Compose Dagger Sample

<div>
  <img align="center" src="app.png" alt="App screenshot" height="640" width="320">
</div>

Technologies used in the application:
- Jetpack Compose
- Dagger 2
- Room

## How to build?

#### Step `1`
Use `Android Studio` to build the application with Gradle or use:
```shell
./gradlew app:assembleDebug
```
#### Step `2`
Generate Bazel build scripts:
```shell
./gradlew migrateToBazel
```
#### Step `3`
Build the application with Bazel:
```shell
bazelisk build //app:bin
```

## Where to start?
#### Step `1`
Check the `buildSrc` directory that contains all the migration templates and logic.
#### Step `2`
Check the root `build.gradle.kts` that contains configuration of the migration plugin.

## Architecture