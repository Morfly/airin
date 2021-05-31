# Airin Examples

## Migrating to Bazel
- [multimodule android](migration/multimodule-android): Example of migrating simple multi-module Android projects.

### Project structure
Example projects are configured to be migrated at once.
To do this, use the following command:
```shell
./gradlew migrateToBazel
```

The most interesting parts of example projects are:
- `buildSrc` directory - where all the Starlark templates are configured.
- Root `build.gradle` file - where Airin gradle plugin is configured

## Generating Bazel projects
- [android databinding](generation/android-databinding): Example of generating an Android project that uses Kotlin language, Data Binding and AndroidX libraries.
### Project structure
Each example project consists of 2 directories:
- `config` - contains the configuration for the project to be generated. Entry point is Main.kt file.
- `generated-project` - contains generated Bazel project.