/*
 * Copyright 2021 Pavlo Stavytskyi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:Suppress("FunctionName")

package org.morfly.airin.sample.template

import org.morfly.airin.starlark.lang.BUILD
import org.morfly.airin.starlark.lang.Label
import org.morfly.airin.starlark.library.android_binary
import org.morfly.airin.starlark.library.artifact


fun root_build_template(
    packageName: String,
    internalDeps: List<Label>
    /**
     *
     */
) = BUILD {

    load("@rules_jvm_external//:defs.bzl", "artifact")

    android_binary(
        name = "app_bin",
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
        deps = internalDeps + list[
                artifact("androidx.databinding:databinding-runtime")
        ]
    )
}