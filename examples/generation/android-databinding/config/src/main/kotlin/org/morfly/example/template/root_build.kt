@file:Suppress("FunctionName")

package org.morfly.example.template

import com.morlfy.airin.starlark.lang.BUILD
import com.morlfy.airin.starlark.lang.Label
import com.morlfy.airin.starlark.lang.Name
import com.morlfy.airin.starlark.library.android_binary
import com.morlfy.airin.starlark.library.artifact


fun root_build_template(
    binaryName: Name,
    packageName: String,
    internalDeps: List<Label>,
    externalDeps: List<String>
    /**
     *
     */
) = BUILD {

    load("@rules_jvm_external//:defs.bzl", "artifact")

    android_binary(
        name = binaryName,
        custom_package = packageName,
        manifest = "//app:src/main/AndroidManifest.xml",
        manifest_values = dict {
            "minSdkVersion" to "23"
            "targetSdkVersion" to "29"
        },
        enable_data_binding = true,
        multidex = "native",
        incremental_dexing = 0,
        dex_shards = 5,
        deps = internalDeps `+` list[
                artifact("androidx.databinding:databinding-runtime")
        ]
    )
}