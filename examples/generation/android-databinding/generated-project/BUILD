load("@rules_jvm_external//:defs.bzl", "artifact")

android_binary(
    name = "example_app",
    custom_package = "com.morfly.airin.sample",
    manifest = "//app:src/main/AndroidManifest.xml",
    manifest_values = {
        "minSdkVersion": "23",
        "targetSdkVersion": "29",
    },
    enable_data_binding = True,
    multidex = "native",
    incremental_dexing = 0,
    dex_shards = 5,
    deps = [
        "//app",
        "//lib1",
        "//lib2",
    ] + [artifact("androidx.databinding:databinding-runtime")],
)
