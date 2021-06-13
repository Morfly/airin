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

package org.morfly.airin.sample.generator

import org.morfly.airin.starlark.writer.FileWriter
import org.morfly.airin.starlark.writer.StarlarkFileWriter
import org.morfly.airin.sample.template.android_databinding_library_build
import org.morfly.airin.sample.template.source.*
import org.morfly.airin.sample.template.manifest.android_manifest_template
import org.morfly.airin.sample.template.manifest.main_android_manifest_template
import java.io.File
import java.util.*


class AndroidDataBindingModuleGenerator(
    private val workspaceDir: File,
    private val rootPackageName: String,
    private val bazelFileWriter: StarlarkFileWriter,
    private val fileWriter: FileWriter
) {

    fun generate(moduleNumber: Int, internalDeps: List<String>, isRootModule: Boolean): String {
        val data = AndroidDataBindingModuleData(
            moduleNumber, rootPackageName, workspaceDir, internalDeps, isRootModule
        )

        generateSourceFiles(data)
        generateManifestFiles(data)
        generateBuild(data)

        return data.label
    }

    private fun generateBuild(data: AndroidDataBindingModuleData) = with(data) {
        val build = android_databinding_library_build(
            relativePath = moduleName,
            dataBindingLayouts = dataBindingLayouts,
            viewModelsWithRes = emptyList(),
            viewModels = viewModels,
            bindingAdapters = bindingAdapters,
            targetName = moduleName,
            packageName = packageName,
            internalDeps = internalDeps,
            externalDeps = externalDeps,
            exportAndroidManifest = isRootModule
        )
        bazelFileWriter.write(workspaceDir, build)
    }

    private fun generateManifestFiles(data: AndroidDataBindingModuleData) = with(data) {
        with(fileWriter) {
            write(
                File("$manifestDir/AndroidManifest.xml"),
                if (isRootModule) main_android_manifest_template(packageName, "App", ".MainActivity")
                else android_manifest_template(packageName)
            )
            write(
                File("$manifestDir/ViewModelsManifest.xml"),
                android_manifest_template("$packageName.viewmodels")
            )
            write(
                File("$manifestDir/DataBindingResourcesManifest.xml"),
                android_manifest_template("$packageName.databinding")
            )
        }
    }

    private fun generateSourceFiles(data: AndroidDataBindingModuleData) = with(data) {
        val adapterName = "text$moduleNumber"
        val vmPropertyName = "text"
        val vmPropertyValue = "Hello from $moduleName"
        val vmVarName = "viewModel"

        with(fileWriter) {
            write(
                File("$resDir/layout/${dataBindingLayoutName(moduleName)}.xml"), binding_layout_template(
                    vmVarName = vmVarName,
                    vmPackageName = packageName,
                    vmClassName = viewModelClassName(moduleName),
                    adapterName = adapterName,
                    vmPropertyName = vmPropertyName
                )
            )
            write(
                File("$srcDir/BindingAdapters.java"),
                binding_adapters_template(packageName, adapterName)
            )
            write(
                File("$srcDir/${viewModelClassName(moduleName)}.kt"), view_model_template(
                    vmPackageName = packageName,
                    vmClassName = viewModelClassName(moduleName),
                    vmPropertyName = vmPropertyName,
                    vmPropertyValue = vmPropertyValue
                )
            )
            write(
                File("$srcDir/${moduleName.replaceFirstChar(Char::uppercase)}.kt"), regular_file_template(
                    packageName = packageName,
                    objectName = moduleName.replaceFirstChar(Char::uppercase),
                    functionName = moduleName,
                    bindingClassName = "Layout${moduleName.replaceFirstChar(Char::uppercase)}Binding",
                    viewModelClassName = viewModelClassName(moduleName),
                    viewModelBindingPropertyName = vmVarName,
                    layoutName = dataBindingLayoutName(moduleName)
                )
            )
            if (isRootModule) {
                write(
                    File("$srcDir/MainActivity.kt"),
                    activity_template(
                        packageName = packageName,
                        className = "MainActivity",
                        bindingClassName = "LayoutAppBinding",
                        layoutName = dataBindingLayoutName(moduleName),
                        viewModelClassName = viewModelClassName(moduleName),
                        viewModelBindingPropertyName = vmVarName
                    )
                )
            }
        }
    }
}

private fun viewModelClassName(prefix: String) =
    "${prefix.replaceFirstChar(Char::uppercase)}ViewModel"

private fun dataBindingLayoutName(suffix: String) =
    "layout_${suffix.lowercase()}"


private data class AndroidDataBindingModuleData(
    val moduleNumber: Int,
    val moduleName: String,
    val packageName: String,
    val label: String,
    val moduleRoot: String,
    val srcDir: String,
    val resDir: String,
    val manifestDir: String,
    val viewModels: List<String>,
    val bindingAdapters: List<String>,
    val dataBindingLayouts: List<String>,
    val internalDeps: List<String>,
    val externalDeps: List<String>,
    val isRootModule: Boolean
)


private const val SRC_SUB_DIR = "src/main/kotlin"
private const val RES_SUB_DIR = "src/main/res"
private const val MANIFEST_SUB_DIR = "src/main"

private fun AndroidDataBindingModuleData(
    moduleNumber: Int,
    rootPackageName: String,
    workspaceDir: File,
    internalDeps: List<String>,
    isRootModule: Boolean
): AndroidDataBindingModuleData {
    val moduleName = if (isRootModule) "app" else "lib$moduleNumber"
    val packageName = "$rootPackageName.$moduleName"
    val packageSubDir = packageName.replace(".", "/")
    val moduleRoot = "${workspaceDir.path}/$moduleName"
    return AndroidDataBindingModuleData(
        moduleNumber = moduleNumber,
        moduleName = moduleName,
        packageName = packageName,
        label = "//$moduleName",
        moduleRoot = moduleRoot,
        srcDir = "$moduleRoot/$SRC_SUB_DIR/$packageSubDir",
        resDir = "$moduleRoot/$RES_SUB_DIR",
        manifestDir = "$moduleRoot/$MANIFEST_SUB_DIR",
        viewModels = listOf("$SRC_SUB_DIR/$packageSubDir/${viewModelClassName(moduleName)}.kt"),
        bindingAdapters = listOf("$SRC_SUB_DIR/$packageSubDir/BindingAdapters.java"),
        dataBindingLayouts = listOf("$RES_SUB_DIR/layout/${dataBindingLayoutName(moduleName)}.xml"),
        internalDeps = internalDeps,
        externalDeps = emptyList(),
        isRootModule = isRootModule
    )
}