# Android Data Binding Sample
Example project for generating Android applications with data-binding enabled modules.
It is possible to configure the number of generated modules, so that it allows testing behavior of large projects.



## Project structure

This example consists of 2 directories:

- `config` - contains the configuration for the project to be generated. Entry point is `Main.kt` file.
- `generated-project` - contains generated Bazel project.

The `generate` function of the `ProjectGenerator` class takes the following arguments:

| Param | Description |
| ----- | ----------- |
| `numOfModules` | Number of modules to be generated |
| `disableStrictJavaDepsFlag` | Adds `--strict_java_deps=OFF` flag to `.bazelrc` if `true` |
| `depsOverlap` | By default each generated module depends on the previous one. This flag specifies the number of additional dependencies before the direct one |