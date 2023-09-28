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

@file:Suppress("PropertyName", "unused")

package org.morfly.airin.starlark.library

import org.morfly.airin.starlark.lang.*
import org.morfly.airin.starlark.lang.api.Argument
import org.morfly.airin.starlark.lang.api.FunctionKind.Statement
import org.morfly.airin.starlark.lang.api.FunctionScope.Build
import org.morfly.airin.starlark.lang.api.FunctionScope.Workspace
import org.morfly.airin.starlark.lang.api.LibraryFunction


@LibraryFunction(
    name = "kt_compiler_plugin",
    scope = [Build],
    kind = Statement
)
private interface KtCompilerPlugin {

    @Argument(required = true)
    val name: Name
    val compile_phase: BooleanType?
    val deps: List<Label?>?
    val id: StringType?
    val options: Map<Key, Value>?
    val stubs_phase: BooleanType?
    val target_embedded_compiler: BooleanType?
    val visibility: List<Label?>?
}


@LibraryFunction(
    name = "kt_jvm_binary",
    scope = [Build],
    kind = Statement
)
private interface KtJvmBinary {

    @Argument(required = true)
    val name: Name
    val data: List<Label?>?
    val deps: List<Label?>?
    val jvm_flags: List<StringType?>?
    val main_class: StringType
    val module_name: StringType?
    val plugins: List<Label?>?
    val resource_jars: List<Label?>?
    val resource_strip_prefix: StringType?
    val resources: List<Label?>?
    val runtime_deps: List<Label?>?
    val srcs: List<Label?>?
    val visibility: List<Label?>?
}


@LibraryFunction(
    name = "kt_jvm_import",
    scope = [Build],
    kind = Statement
)
private interface KtJvmImport {

    @Argument(required = true)
    val name: Name
    val deps: List<Label?>?
    val exported_compiler_plugins: List<Label?>?
    val exports: List<Label?>?
    val jar: Label?
    val jars: List<Label?>?
    val neverlink: BooleanType?
    val runtime_deps: List<Label?>?
    val srcjar: Label?
    val visibility: List<Label?>?
}


@LibraryFunction(
    name = "kt_jvm_library",
    scope = [Build],
    kind = Statement
)
private interface KtJvmLibrary {

    @Argument(required = true)
    val name: Name
    val srcs: List<Label?>?
    val deps: List<Label?>?
    val exports: List<Label?>?
    val resources: List<Label?>?
    val runtime_deps: List<Label?>?
    val exported_compiler_plugins: List<Label?>?
    val data: List<Label?>?
    val module_name: StringType?
    val neverlink: BooleanType?
    val plugins: List<Label?>?
    val resource_jars: List<Label?>?
    val resource_strip_prefix: StringType?
    val visibility: List<Label?>?
}


@LibraryFunction(
    name = "kt_android_library",
    scope = [Build],
    kind = Statement
)
private interface KtAndroidLibrary {

    @Argument(required = true)
    val name: Name
    val srcs: List<Label?>?
    val custom_package: StringType?
    val manifest: Label?
    val exports_manifest: BooleanType?
    val resource_files: List<Label?>?
    val enable_data_binding: BooleanType?
    val deps: List<Label?>?
    val exports: List<Label?>?
    val visibility: List<Label?>?
    val plugins: List<Label?>?
}


@LibraryFunction(
    name = "define_kt_toolchain",
    scope = [Build, Workspace],
    kind = Statement
)
private interface DefineKtToolchain {

    @Argument(required = true)
    val name: Name
    val api_version: StringType?
    val jvm_target: StringType?
    val language_version: StringType?
    val experimental_use_abi_jars: BooleanType?
    val javac_options: Label?
    val kotlinc_options: Label?
}

@LibraryFunction(
    name = "kt_register_toolchains",
    scope = [Workspace],
    kind = Statement
)
private interface KtRegisterToolchains


@LibraryFunction(
    name = "kotlin_repositories",
    scope = [Workspace],
    kind = Statement
)
private interface KotlinRepositories {

    val is_bzlmod: StringType?
    val compiler_repository_name: StringType?
    val ksp_repository_name: StringType?
    val compiler_release: Map<Key, Value>?
    val ksp_compiler_release: Map<Key, Value>?
}


@LibraryFunction(
    name = "kt_kotlinc_options",
    scope = [Build, Workspace],
    kind = Statement
)
private interface KtKotlincOptions {

    @Argument(required = true)
    val name: Name
    // TODO add other args
}


@LibraryFunction(
    name = "kt_javac_options",
    scope = [Build, Workspace],
    kind = Statement
)
private interface KtJavacOptions {

    @Argument(required = true)
    val name: Name
    // TODO add other args
}

//private interface