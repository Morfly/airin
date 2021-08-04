# Starlark Library Generation
Airin provides by default a set of Bazel rules and functions that include Java, Kotlin, Android use cases and more.
All of them are available in `airin-starlark-stdlib` artifact.

These functions are represented with a typesafe Kotlin DSL that can be used for generating the corresponding code in Starlark. 
If you need to usee additional rules and functions for your build scripts, Airin provides 
the mechanism Kotlin DSL for them.

With the power of Kotlin symbol processing (ksp) `airin-starlark-libgen` artifact enables this functionallity.

## Getting started

In Gradle module that should contain the library, open `build.gradle(.kts)` file and add `ksp` plugin and required 
repositories.
```groovy
plugins {
    id "com.google.devtools.ksp" version "1.5.20-1.0.0-beta04"
}

repositories {
    mavenCentral()
    google()
}
```

In `dependencies` section add the following:
```groovy
// Starlark template engine
implementation "org.morfly.airin:airin-starlark:0.4.0"

// Library generator
ksp "org.morfly.airin:airin-starlark-libgen:0.4.0"
```

## Generating functions
In order to generate the function a `@LibraryFunction` annotation must be applied to the Kotlin `interface`.

```kotlin
@LibraryFunction(
    name = "android_binary",
    scope = [Build],
    kind = Statement,
)
interface AndroidBinary {
    
    @Argument(required = true)
    val name: StringType
    val srcs: ListType<Label?>?
    val deps: ListType<Label?>?
    val manifest_values: DictionaryType<Key, Value>?
}
```
The above configuration will generate two types of Kotlin DSL functions that use `round` and `curly` brackets. 
Both will generate the same Starlark function.

The first one uses `round` backets and is more similar to its Sarlark counterpart:
```kotlin
fun build() = BUILD.bazel {
    
    android_binary(
        name = "app",
        srcs = glob("src/main/kotlin/**/*.kt"),
        deps = list[":lib"],
    )
}
```
The second one uses `curly` brackets and provides more flexibility while describing build scripts:
```kotlin
fun build(useLib: Boolean) = BUILD.bazel {
    
    android_binary {
        name = "app"
        srcs = glob("src/main/kotlin/**/*.kt")
        if(useLib) {
            deps = list[":lib"]
        }
        "manifest_values" `=` {
            "minSdkVersion" to "29"
        }
    }
}
```
You can use either of them depending on your use case, as they will output the same Starlark code.

### Generation configuration

The `@LibraryFunction` annotation has the following properties:

|   param    | type              | enum options                     | description                                                                                          |
|:----------:|-------------------|----------------------------------|------------------------------------------------------------------------------------------------------|
| `name`     | `String`          |                                  | Name of the generated function.                                                                      |
| `scope`    | [`FunctionScope`] | `Build`  `Workspace`  `Starlark` | Defines whether the function targets `BUILD`, `WORKSPACE` or `.bzl` files. Can be combined.          |
| `kind`     | `FunctionKind`    | `Statement`  `Expression`        | `Statement` is more applicable to rules while `Expression` can be used for functions that return some value such as `glob`. |
| `brackets` | [`BracketsKind`]  | `Round`  `Curly`                 | Defines the type of DSL function by brackets. Can be combined.                                       |

### Function arguments
Each propety of the interface will be considered as a function argument except the one annotated with `@Returns` annotation.

It is also possible to annotate the property with the `@Argument` annotation to adjust the behavior of the corresponding argument.
However, it is not mandatory.

The `@Argument` annotation has the following properties:

|      param       | type      | optional | description                                                                                                                         |
|:----------------:|-----------|:--------:|-------------------------------------------------------------------------------------------------------------------------------------|
| `underlyingName` | `String`  | +        | Name that will be generated for the Starlark function. If not set the name will be taken from the property itself.                  |
| `required`       | `Boolean` | +        | Defines whether the argument is mandatory.                                                                                          |
| `vararg`         | `Boolean` | +        | Defines whether Kotlin DSL representation will generate this argument as `vararg`. The function can have only one such an argument. |

The example below shows the use of `vararg` parameter:
```kotlin
@LibraryFunction(
    name = "glob",
    scope = [Build, Workspace, Starlark],
    kind = Expression
)
interface Glob {
    
    @Argument(vararg = true)
    val include: ListType<Label>
    
    @Returns
    val returns: ListType<Label>
}
```
This will generate the function named `glob` that has `include` argument as Kotlin `vararg`. 
> Note. Airin will also generate the second `glob` function with the regular `include` arg of `ListType`.
```kotlin
val srcs: ListType<StringType> = glob(
    "src/main/**/*.kt", 
    "src/main/**/*.java"
)
```

### Return type
The `@Returns` annotation specifies the return type of the generated function. Only function of `Expression` kind can have 
explicitly specified return type. `Statement` functions have `Unit` return type by default.

The `@Returns` annotation has the following properties:

| param   | type         | enum options      | description                                                                                                                                           |
|:-------:|--------------|-------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------|
| `kind`  | `ReturnKind` | `Type`  `Dynamic` | Defines whether the generated Kotlin DSL function has a `specified return type`. Otherwise it will be `dynamically` inferred depending on the caller. |

The example below shows the use of a `Dynamic` return kind:

```kotlin
@LibraryFunction(
    name = "select",
    scope = [Build],
    kind = Expression
)
interface Glob {
    
    @Argument(underlyingName = "")
    val select: DictionaryType<Key, Value>?

    // Property name and type are ignored 
    @Returns(kind = Dynamic)
    val returns: Any
}
```
This will generate the function return type of which will be infered depending on the caller.
```kotlin
val stringArg: StringType = select(dict{})

val listArg: ListType<StringType> = select(dict{})
```

## Allowed types
Arguments of generated functions could be of the following types:
- `StringType` and aliases: `Label`, `Name`
- `NumberType`
- `BooleanType`
- `ListType`
- `DictionaryType`
- `TupleType`
- `Any`

Item type of `ListType` should follow the same rule above.

In addition, types of keys and values of `DictionaryType` should be:
- `Key` for keys
- `Value` for values

All the types above (except `Any`) are imported from the `org.morfly.airin.starlark.lang` package.