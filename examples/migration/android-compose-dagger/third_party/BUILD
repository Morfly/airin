load("@rules_android//android:rules.bzl", "android_binary")
load("@rules_jvm_external//:defs.bzl", "artifact")

package(default_visibility = ["//visibility:public"])

aar_import(
    name = "room_runtime",
    aar = "@maven_secondary//:v1/https/maven.google.com/androidx/room/room-runtime/2.3.0/room-runtime-2.3.0.aar",
    deps = [
        artifact("androidx.sqlite:sqlite"),
        artifact("androidx.arch.core:core-common"),
        artifact("androidx.lifecycle:lifecycle-livedata-ktx"),
    ],
)

java_import(
    name = "kotlin_reflect",
    jars = ["kotlin-reflect-1.5.10.jar_desugared.jar"],
)

java_library(
    name = "sun_misc_stubs",
    neverlink = True,
    srcs = [
        "sun/misc/Signal.java",
        "sun/misc/SignalHandler.java",
    ],
    visibility = ["//visibility:private"],
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