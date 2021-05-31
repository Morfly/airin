# Airin Starlark Template Engine

Airin provides a declarative, typesafe Starlark template engine with which you can easily describe you Bazel build
scripts using Kotlin.

## Overview

Here is the example of a basic usage of Airin Starlark template engine.

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

fun main() {
    val buildFile = java_build("app", "src/main/java", "com.morfly.airin.Main")
    val path = File("path/to/the/file")
    StarlarkFileWriter.write(path, buildFile)
}
```
The example above will generate a `BUILD.bazel` file in `path/to/the/file`.

See [example1](../examples/generation/android-databinding/config/src/main/kotlin/org/morfly/example/template/android_databinding_library_build.kt) 
and [example2](../examples/migration/multimodule-android/buildSrc/src/main/kotlin/template/android_workspace.kt) 
for more advanced usages of Airin template engine or check the complete [example projects](../examples). 

## Enter Starlark Scope

In order to enter the Starlark scope and start declaring templates for Bazel build scripts there is a set of Kotlin
functions for that.

### BUILD files

One of them allows creating `BUILD` files.

```kotlin
BUILD {
    // your template code
}
```

Another allows creating the `BUILD.bazel` files

```kotlin
BUILD.bazel {
    // your template code
}
```

### WORKSPACE files

Similar functions allow creating `WORKSPACE` files.

```kotlin
WORKSPACE {
    // your template code
}
```

```kotlin
WORKSPACE.bazel {
    // your template code
}
```

## Variables and Assignments

To declare a variable initialization `by` operator must be used, so that the following Kotlin statement. Only `val`
properties are allowed here.

```kotlin
// kotlin
val PATH by "//src/Main.kt"
```

```python
# starlark
PATH = "//src/Main.kt"
```

### Reassigning variables

In order to reassign variable use `=` operator with _backticks_:

```kotlin
// kotlin
PATH `=` "//src/New.kt"
```

```python
# starlark
PATH = "//src/New.kt"
```

### Variables with dynamic names

In case you need to define a name for your variable dynamically based on some argument, the `=` operator with _backticks_
can be used.

```kotlin
// kotlin
val TARGET_NAME = "MODULE_1"

val FILES by "${TARGET_NAME}_FILES" `=` list["src/file.txt"]
```

```python
# starlark
MODULE_1_FILES = ["src/file.txt"]
```

In the example above, the `FILES` variable is of `List` type so that it can be used in any place where lists are
expected. All further references to it will produce the `MODULE_1_FILES` name in Starlark.

## Collections

### Lists

List in Airin can be declared with `list` function using square brackets.

```kotlin
// kotlin
list[1, 2, 3, 4, 5]
```

Or round brackets.

```kotlin
// kotlin
list(1, 2, 3, 4, 5)
```

Both variants are equivalent to the list expression in Starlark.

```python
# starlark
[1, 2, 3, 4, 5]
```

For empty lists only round brackets are available.

```kotlin
// kotlin
list()
```

### Dictionaries

Dicrionaries in Airin can be described with the `dict` function with curly brackets.

```kotlin
// kotlin
dict { "key1" to "value1"; "key2" to "value2" }
```

```python
# starlark
{"key1": "value1", "key2": "value2"}
```

There is also available a shortened form of dictionary declarations. Note, that it is applicable only when used after
dynamic operators with _backticks_ such as `=` or `+` or as function argument in some cases.

```kotlin
MY_DICTIONARY `=` { "key1" to "value1"; "key2" to "value2" }
```

### Tuples

Tuples can be declared with a `tuple` function.

```kotlin
// kotlin
tuple("value1", 2, 3.0)
```

```python
# starlark
("value1", 2, 3.0)
```

## Comprehensions

### List Comprehension

Airin provides type safe list comprehensions that conform to `List` type and can be used in any place where lists are expected.

```kotlin
// kotlin
val LIST by list["1", "2", "3", "4", "5"]

"i" `in` LIST take { i -> i }
```

```python
# starlark
LIST = ["1", "2", "3", "4", "5"]

[i for i in LIST]
```

It is also possible to declare nested list comprehension with using `for`.

```kotlin
// kotlin
val LIST by list[list["1"], list["2"], list["3"]]

"sublist" `in` LIST `for` { sublist ->
    "i" `in` sublist take { i -> i }
}
```

```starlark
# starlark
[i for sublist in LIST for i in sublist]
```




## Concatenations

In order to declare a concatenation, a `+` operator with _backticks_ can be used.

```kotlin
// kotlin
list["item1"] `+` list["item2"]
```

```python
# starlark
["item1"] + ["item2"]
```

This operation is typesafe and can be used with all components that conform to the specific type. 

For example, _list expressions_, _list variables_ and _list comprehensions_ are all of type `List`, so in case they have
the same item type, they all can be concatenated.

```kotlin
// kotlin
val LIST by list["item"]

list["item"] `+` LIST `+` ("i" `in` LIST take { i -> i })
```
```python
# starlark
LIST = ["item"]

["item"] + LIST + [i for i in LIST]
```

## Load Statements

Load statements look exactly the same as in Starlark itself.

```kotlin
// kotlin
load("@rules_java//java:defs.bzl", "java_binary")
```

```python
# starlark
load("@rules_java//java:defs.bzl", "java_binary")
```

> Note: symbol aliases are not supported yet.

## Rules and Function Calls

Airin has a library of commonly used Starlark rules and functions including Java, Kotlin, Android platforms and more.

To use a function statement call a corresponding Kotlin function and pass arguments in round brackets.

```kotlin
// kotlin
java_binary(
    name = "app",
    srcs = glob("src/main/java/**/*.java"),
    main_class = "com.morfly.Main",
    deps = list["//lib"]
)
```

If you want to use arguments that are not present in the library, there is an option of specifying custom arguments. To
do this, instead of round brackets use curly brackets. Specify the argument you want by a string and use `=` operator
with _backticks_.

```kotlin
// kotlin
java_binary {
    name = "app"
    "custom_argument" `=` list["value1", "value2"]
}
```

It is also possible to dynamically declare the name of a function statement with using the same approach.

```kotlin
// kotlin
"java_binary" {
    "name" `=` "app"
    "custom_argument" `=` list["value1", "value2"]
}
```