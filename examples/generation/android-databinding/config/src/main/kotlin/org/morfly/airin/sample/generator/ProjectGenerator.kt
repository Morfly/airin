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

@file:Suppress("SpellCheckingInspection")

package org.morfly.airin.sample.generator

import org.morfly.airin.starlark.writer.FileWriter
import org.morfly.airin.starlark.writer.StarlarkFileWriter
import org.morfly.airin.sample.template.android_databinding_workspace
import org.morfly.airin.sample.template.other.android_databinding_bazelrc
import org.morfly.airin.sample.template.root_build_template
import org.morfly.airin.sample.template.tools_build
import java.io.File
import java.util.*


const val GENERATED_PROJECT_ROOT_DIR = "../generated-project"
private const val ROOT_PACKAGE_NAME = "org.morfly.airin.sample"


/**
 *
 */
class ProjectGenerator {

    private val internalDeps = LinkedList<String>()

    private val workspaceDir = File(GENERATED_PROJECT_ROOT_DIR)

    private val bazelFileWriter = StarlarkFileWriter

    private val fileWriter = FileWriter

    private val moduleGenerator = AndroidDataBindingModuleGenerator(
        workspaceDir,
        rootPackageName = ROOT_PACKAGE_NAME,
        bazelFileWriter = bazelFileWriter,
        fileWriter = fileWriter
    )

    /**
     *
     */
    fun generate(numOfModules: Int, disableStrictJavaDeps: Boolean = false, depsOverlap: Int = 0) {
        require(numOfModules > 0) { "number of data binding modules must be positive." }

        workspaceDir.mkdir()

        generateToolsBuild()
        generateWorkspace()
        generateBazelRc(disableStrictJavaDeps)
        generateBazelversion()

        for (i in (numOfModules - 1) downTo 0) {
            val label = moduleGenerator.generate(i, internalDeps, i == 0)
            if (internalDeps.size > depsOverlap) {
                internalDeps
                    .subList(depsOverlap, internalDeps.size)
                    .clear()
            }
            internalDeps.add(0, label)
        }

        generateRootBuild()

        internalDeps.clear()
    }

    private fun generateWorkspace() {
        val workspace = android_databinding_workspace(
            listOf(
                "androidx.databinding:databinding-adapters:3.4.2",
                "androidx.databinding:databinding-common:3.4.2",
                "androidx.databinding:databinding-compiler:3.4.2",
                "androidx.databinding:databinding-runtime:3.4.2",
                "androidx.annotation:annotation:1.1.0",
            )
        )
        bazelFileWriter.write(workspaceDir, workspace)
    }

    private fun generateBazelRc(disableStrictJavaDeps: Boolean) {
        val bazelRc = android_databinding_bazelrc(
            androidToolsLocation = "${workspaceDir.canonicalPath}/tools/android/android_tools",
            disableStrictJavaDeps = disableStrictJavaDeps
        )
        val file = File("${workspaceDir.path}/.bazelrc")
        fileWriter.write(file, bazelRc)
    }

    private fun generateBazelversion() {
        val content = "4.0.0"
        val file = File("${workspaceDir.path}/.bazelversion")
        fileWriter.write(file, content)
    }

    private fun generateToolsBuild() {
        File("src/main/resources/android_tools").copyRecursively(
            target = File("$GENERATED_PROJECT_ROOT_DIR/tools/android/android_tools"),
            overwrite = true
        )
        File("src/main/resources/kotlin").copyRecursively(
            target = File("$GENERATED_PROJECT_ROOT_DIR/tools/kotlin"),
            overwrite = true
        )
        val toolsBuild = tools_build()
        bazelFileWriter.write(workspaceDir, toolsBuild)
    }

    private fun generateRootBuild() {
        val build = root_build_template(
            packageName = ROOT_PACKAGE_NAME,
            internalDeps = internalDeps,
        )
        bazelFileWriter.write(workspaceDir, build)
    }
}