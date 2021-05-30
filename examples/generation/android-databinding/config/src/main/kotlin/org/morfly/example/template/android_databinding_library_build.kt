@file:Suppress("LocalVariableName", "FunctionName", "SpellCheckingInspection")

package org.morfly.example.template

import com.morlfy.airin.starlark.lang.BUILD
import com.morlfy.airin.starlark.lang.Label
import com.morlfy.airin.starlark.library.*


fun android_databinding_library_build(
    relativePath: String,
    dataBindingLayouts: List<Label>,
    viewModelsWithRes: List<Label>,
    viewModels: List<Label>,
    bindingAdapters: List<Label>,
    targetName: String,
    packageName: String,
    internalDeps: List<Label>,
    externalDeps: List<String>,
    exportAndroidManifest: Boolean
    /**
     *
     */
) = BUILD(relativePath) {
    val TARGET_NAME = targetName.toUpperCase()

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

    val EXCLUDED_TARGET_FILES by "EXCLUDED_${TARGET_NAME}_FILES" `=` VIEW_MODELS `+` VIEW_MODELS_WITH_RES_IMPORTS `+` BINDING_ADAPTERS

    val TARGET_FILES_WITH_RESOURCE_IMPORTS by "${TARGET_NAME}_FILES_WITH_RESOURCE_IMPORTS" `=` glob(
        "src/main/kotlin/**/*.kt",
        "src/main/kotlin/**/*.java",
        exclude = EXCLUDED_TARGET_FILES
    )

    val TARGET_FILES by "${TARGET_NAME}_FILES" `=` ("app_files_with_res_imports" `in` TARGET_FILES_WITH_RESOURCE_IMPORTS take {
        "modify_imports_in_" `+` it[0..-3]
    })

    "file" `in` TARGET_FILES_WITH_RESOURCE_IMPORTS take { file ->

        genrule(
            name = "modify_imports_in_" `+` file[0..-3],
            srcs = list[file],
            outs = list[file[0..-3] `+` "_synthetic.kt"],
            cmd = """
            cat $(SRCS) |
            sed 's/import $packageName.databinding./import $packageName.databinding.databinding./g' > $(OUTS)
            """.trimIndent()
        )
    }

    android_library(
        name = "resources",
        custom_package = "$packageName.res",
        manifest = "src/main/AndroidManifest.xml",
        exports_manifest = true,
        resource_files = glob(
            "src/main/res/**",
            exclude = DATABINDING_LAYOUTS
        ),
        deps = internalDeps + externalDeps.map { artifact(it) }
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
                artifact("androidx.annotation:annotation")
        ]
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
                artifact("androidx.databinding:databinding-runtime")
        ]
    )

    kt_android_library(
        name = targetName,
        srcs = TARGET_FILES,
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
        ]
    )

    if (exportAndroidManifest)
        exports_files(
            "src/main/AndroidManifest.xml",
            visibility = list("//:__pkg__")
        )
}