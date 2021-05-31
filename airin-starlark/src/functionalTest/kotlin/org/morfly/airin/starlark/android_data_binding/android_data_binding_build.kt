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

@file:Suppress("LocalVariableName", "EmptyRange", "SpellCheckingInspection")

package org.morfly.airin.starlark.android_data_binding

import org.morfly.airin.starlark.lang.BUILD
import org.morfly.airin.starlark.lang.Label
import org.morfly.airin.starlark.lang.bazel
import org.morfly.airin.starlark.library.*


fun android_data_binding_build(
    dataBindingLayouts: List<Label>,
    viewModelsWithRes: List<Label>,
    viewModels: List<Label>,
    bindingAdapters: List<Label>,
    targetName: String,
    packageName: String,
    internalDeps: List<Label>,
    externalDeps: List<String>,
    exportAndroidManifest: Boolean
) = BUILD.bazel {
    val TARGET_NAME = targetName.uppercase()

    load("//tools/kotlin:kotlin.bzl", "kt_android_library")
    load("@rules_jvm_external//:defs.bzl", "artifact")

    `package`(default_visibility = list["//visibility:public"])

    val DATABINDING_LAYOUTS by dataBindingLayouts

    val VIEW_MODELS_WITH_RES_IMPORTS by viewModelsWithRes

    val VIEW_MODELS by viewModels `+` ("view_model_with_res_imports" `in` VIEW_MODELS_WITH_RES_IMPORTS take {
        "modify_imports_in_" `+` it[0..-3]
    })


    "file" `in` VIEW_MODELS_WITH_RES_IMPORTS take { file ->
        genrule(
            name = "modify_imports_in_" `+` file[0..-3],
            srcs = list[file],
            outs = list[file[0..-3] `+` "_synthetic.kt"],
            cmd = """
            cat $(SRCS) |
            sed 's/import $packageName.R/import $packageName.viewmodels.R/g' > $(OUTS)
            """.trimIndent()
        )
    }

    val BINDING_ADAPTERS by bindingAdapters

    val EXCLUDED_FILES by "EXCLUDED_${TARGET_NAME}_FILES" `=` VIEW_MODELS `+` VIEW_MODELS_WITH_RES_IMPORTS `+` BINDING_ADAPTERS

    val FILES_WITH_RESOURCE_IMPORTS by "${TARGET_NAME}_FILES_WITH_RESOURCE_IMPORTS" `=` glob(
        "src/main/kotlin/**/*.kt",
        "src/main/kotlin/**/*.java",
        exclude = EXCLUDED_FILES
    )

    val FILES by "${TARGET_NAME}_FILES" `=` ("app_files_with_res_imports" `in` FILES_WITH_RESOURCE_IMPORTS take {
        "modify_imports_in_" `+` it[0..-3]
    })

    "file" `in` FILES_WITH_RESOURCE_IMPORTS take { file ->
        genrule(
            name = "modify_imports_in_" `+` file[0..-3],
            srcs = list[file],
            outs = list[file[0..-3] `+` "_synthetic.kt"],
            cmd = """
            cat $(SRCS) |
            sed 's/import $packageName.databinding./import $packageName.databinding.databinding./g' > $(OUTS)
            """.trimIndent(),
        )
    }

    android_library(
        name = "resources",
        custom_package = "$packageName.res",
        manifest = "src/main/AndroidManifest.xml",
        exports_manifest = true,
        resource_files = glob(
            "src/main/res/**",
            exclude = DATABINDING_LAYOUTS,
        ),
        deps = internalDeps + externalDeps.map { artifact(it) },
    )

    kt_android_library(
        name = "view_models",
        srcs = VIEW_MODELS,
        custom_package = "$packageName.viewmodels",
        manifest = "src/main/ViewModelsManifest.xml",
        enable_data_binding = true,
        deps = list[
                ":resources",
                artifact("androidx.databinding:databinding-common"),
                artifact("androidx.databinding:databinding-adapters"),
                artifact("androidx.databinding:databinding-runtime"),
                artifact("androidx.annotation:annotation"),
        ],
    )

    android_library(
        name = "databinding_resources",
        srcs = BINDING_ADAPTERS,
        custom_package = "$packageName.databinding",
        enable_data_binding = true,
        exports_manifest = true,
        manifest = "src/main/DataBindingResourcesManifest.xml",
        resource_files = DATABINDING_LAYOUTS,
        deps = list[
                ":resources",
                ":view_models",
                artifact("androidx.databinding:databinding-adapters"),
                artifact("androidx.databinding:databinding-common"),
                artifact("androidx.databinding:databinding-runtime"),
        ],
    )

    kt_android_library(
        name = "lib1",
        srcs = FILES,
        custom_package = packageName,
        manifest = "src/main/AndroidManifest.xml",
        exports_manifest = true,
        enable_data_binding = true,
        visibility = list["//visibility:public"],
        deps = list[
                ":resources",
                ":view_models",
                ":databinding_resources",
                artifact("androidx.databinding:databinding-common"),
                artifact("androidx.databinding:databinding-adapters"),
                artifact("androidx.databinding:databinding-runtime"),
                artifact("androidx.annotation:annotation"),
        ],
    )

    if (exportAndroidManifest)
        exports_files(
            "src/main/AndroidManifest.xml",
            visibility = list("//:__pkg__")
        )
}