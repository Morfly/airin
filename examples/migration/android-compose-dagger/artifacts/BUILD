load("@rules_android//android:rules.bzl", "android_binary")
load("@rules_jvm_external//:defs.bzl", "artifact")

aar_import(
    name = "room_runtime",
    aar = "room-runtime-2.3.0.aar",
    visibility = ["//visibility:public"],
    deps = [
        artifact("androidx.sqlite:sqlite"),
        artifact("androidx.arch.core:core-common"),
        artifact("androidx.lifecycle:lifecycle-livedata-ktx"),
    ],
)

aar_import(
    name = "room_ktx",
    aar = "room-ktx-2.3.0.aar",
    visibility = ["//visibility:public"],
)

java_import(
    name = "kotlin_reflect",
    jars = [
        "kotlin-reflect-1.5.10.jar_desugared.jar",
    ],
    visibility = ["//visibility:public"],
)