load(
    "@rules_java//java:defs.bzl",
    "java_library",
    "java_import",
)
load("@rules_jvm_external//:defs.bzl", "artifact")

package(default_visibility = ["//visibility:public"])

java_library(
    name = "sun_misc_stubs",
    srcs = [
        "sun/misc/Signal.java",
        "sun/misc/SignalHandler.java",
    ],
    visibility = ["//visibility:private"],
    neverlink = True,
)

java_import(
    name = "kotlinx_coroutines_core_jvm",
    jars = ["@maven_secondary//:v1/https/repo1.maven.org/maven2/org/jetbrains/kotlinx/kotlinx-coroutines-core-jvm/1.5.1/kotlinx-coroutines-core-jvm-1.5.1.jar"],
    deps = [
        ":sun_misc_stubs",
        artifact("org.jetbrains.kotlin:kotlin-stdlib-common"),
        artifact("org.jetbrains.kotlin:kotlin-stdlib-jdk8"),
    ],
)