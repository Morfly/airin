# Starlark Template Engine

### BUILD files
```kotlin
BUILD {
    ...
}
```
```kotlin
BUILD.bazel {
    ...
}
```
```kotlin
BUILD("relative/path") { ... }
```
```kotlin
BUILD.bazel("relative/path") { ... }
```
### WORKSPACE files
```kotlin
WORKSPACE {
    ...
}
```
```kotlin
WORKSPACE.bazel {
    ...
}
```
```kotlin
WORKSPACE("relative/path") { ... }
```
```kotlin
WORKSPACE.bazel("relative/path") { ... }
```

## Variables and Assignments
To declare a variable initialization `by` operator must be used, 
so that the following Kotlin statement. Only `val` properties are allowed 
```kotlin
val PATH by "//src/Main.kt"
```
is equivalent to Starlark's:

```python
PATH = "//src/main/Main.kt"
```
### Reassigning variables
In order to reassign variable use `=` operator with backticks:
```kotlin
PATH `=` "//src/New.kt"
```
is equivalent to:
```python
PATH = "//src/New.kt"
```

## Collections

### Lists
Square brackets
```kotlin
list[1, 2, 3, 4, 5] // kotlin
```
Round brackets
```kotlin
list(1, 2, 3, 4, 5) // kotlin
```
Equivalent to:
```python
[1, 2, 3, 4, 5] // starlark
```
Empty list
```kotlin
list()
```
### Dictionaries
```kotlin
dict { "key1" to "value1"; "key2" to "value2" }
```
```kotlin
dict {} `+` { "key1" to "value1"; "key2" to "value2" }
```
### Tuples

## Concatenations

## Load Statements

## Rules and Function Calls

## Comprehensions

### List Comprehension
```kotlin
"file" `in` FILES take {
    
}
```


