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

package org.morfly.airin.starlark.android_data_binding

import org.morfly.airin.starlark.TestWriter
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe


class AndroidDataBindingTemplateTests : ShouldSpec({
    val writer = TestWriter()

    context("android_data_binding_build template") {
        val dataBindingLayouts = listOf("src/main/res/layout/layout_lib1.xml")
        val viewModelsWithRes = emptyList<String>()
        val viewModels = listOf("src/main/kotlin/org/morfly/airin/test/lib1/Lib1ViewModel.kt")
        val bindingAdapters = listOf("src/main/kotlin/org/morfly/airin/test/lib1/BindingAdapters.java")
        val targetName = "lib1"
        val packageName = "org.morfly.airin.test.lib1"
        val internalDeps = listOf(":lib2")
        val externalDeps = listOf("io.reactivex.rxjava3:rxjava:3.0.12")
        val exportAndroidManifest = true

        val file = android_data_binding_build(
            dataBindingLayouts, viewModelsWithRes, viewModels, bindingAdapters, targetName, packageName, internalDeps,
            externalDeps, exportAndroidManifest
        )

        val qqq = "\"\"\""
        val expectedResult = """
            load("//tools/kotlin:kotlin.bzl", "kt_android_library")
            load("@rules_jvm_external//:defs.bzl", "artifact")
            
            package(default_visibility = ["//visibility:public"])
            
            DATABINDING_LAYOUTS = ["src/main/res/layout/layout_lib1.xml"]
            VIEW_MODELS_WITH_RES_IMPORTS = []
            VIEW_MODELS = ["src/main/kotlin/org/morfly/airin/test/lib1/Lib1ViewModel.kt"] + [
                "modify_imports_in_" + view_model_with_res_imports[0:-3]
                for view_model_with_res_imports in VIEW_MODELS_WITH_RES_IMPORTS
            ]
            
            [
                genrule(
                    name = "modify_imports_in_" + file[0:-3],
                    srcs = [file],
                    outs = [file[0:-3] + "_synthetic.kt"],
                    cmd = $qqq
                    cat $(SRCS) |
                    sed 's/import org.morfly.airin.test.lib1.R/import org.morfly.airin.test.lib1.viewmodels.R/g' > $(OUTS)
                    $qqq,
                )
                for file in VIEW_MODELS_WITH_RES_IMPORTS
            ]
            
            BINDING_ADAPTERS = ["src/main/kotlin/org/morfly/airin/test/lib1/BindingAdapters.java"]
            EXCLUDED_LIB1_FILES = VIEW_MODELS + VIEW_MODELS_WITH_RES_IMPORTS + BINDING_ADAPTERS
            LIB1_FILES_WITH_RESOURCE_IMPORTS = glob(
                [
                    "src/main/kotlin/**/*.kt",
                    "src/main/kotlin/**/*.java",
                ],
                exclude = EXCLUDED_LIB1_FILES,
            )
            LIB1_FILES = [
                "modify_imports_in_" + app_files_with_res_imports[0:-3]
                for app_files_with_res_imports in LIB1_FILES_WITH_RESOURCE_IMPORTS
            ]
            
            [
                genrule(
                    name = "modify_imports_in_" + file[0:-3],
                    srcs = [file],
                    outs = [file[0:-3] + "_synthetic.kt"],
                    cmd = $qqq
                    cat $(SRCS) |
                    sed 's/import org.morfly.airin.test.lib1.databinding./import org.morfly.airin.test.lib1.databinding.databinding./g' > $(OUTS)
                    $qqq,
                )
                for file in LIB1_FILES_WITH_RESOURCE_IMPORTS
            ]
            
            android_library(
                name = "resources",
                custom_package = "org.morfly.airin.test.lib1.res",
                manifest = "src/main/AndroidManifest.xml",
                exports_manifest = True,
                resource_files = glob(
                    ["src/main/res/**"],
                    exclude = DATABINDING_LAYOUTS,
                ),
                deps = [
                    ":lib2",
                    artifact("io.reactivex.rxjava3:rxjava:3.0.12"),
                ],
            )
            
            kt_android_library(
                name = "view_models",
                srcs = VIEW_MODELS,
                custom_package = "org.morfly.airin.test.lib1.viewmodels",
                manifest = "src/main/ViewModelsManifest.xml",
                enable_data_binding = True,
                deps = [
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
                custom_package = "org.morfly.airin.test.lib1.databinding",
                manifest = "src/main/DataBindingResourcesManifest.xml",
                exports_manifest = True,
                resource_files = DATABINDING_LAYOUTS,
                enable_data_binding = True,
                deps = [
                    ":resources",
                    ":view_models",
                    artifact("androidx.databinding:databinding-adapters"),
                    artifact("androidx.databinding:databinding-common"),
                    artifact("androidx.databinding:databinding-runtime"),
                ],
            )
            
            kt_android_library(
                name = "lib1",
                srcs = LIB1_FILES,
                custom_package = "org.morfly.airin.test.lib1",
                manifest = "src/main/AndroidManifest.xml",
                exports_manifest = True,
                enable_data_binding = True,
                deps = [
                    ":resources",
                    ":view_models",
                    ":databinding_resources",
                    artifact("androidx.databinding:databinding-common"),
                    artifact("androidx.databinding:databinding-adapters"),
                    artifact("androidx.databinding:databinding-runtime"),
                    artifact("androidx.annotation:annotation"),
                ],
                visibility = ["//visibility:public"],
            )
            
            exports_files(
                ["src/main/AndroidManifest.xml"],
                visibility = ["//:__pkg__"],
            )
        """.trimIndent()

        writer.write(null, file) shouldBe expectedResult
    }
})