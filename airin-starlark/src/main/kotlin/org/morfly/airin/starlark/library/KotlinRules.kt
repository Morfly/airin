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

@file:Suppress("SpellCheckingInspection", "PropertyName", "FunctionName")

package org.morfly.airin.starlark.library

import org.morfly.airin.starlark.elements.*
import org.morfly.airin.starlark.lang.*
import org.morfly.airin.starlark.lang.feature.FunctionCallContext
import org.morfly.airin.starlark.lang.feature.registerFunctionCallStatement


// ===== kt_compiler_plugin =====

/**
 *
 */
fun BuildContext.kt_compiler_plugin(
    name: Name,
    compile_phase: BooleanType? = UnspecifiedBoolean,
    deps: List<Label?>? = UnspecifiedList,
    id: StringType? = UnspecifiedString,
    options: Map<Key, Value>? = UnspecifiedDictionary,
    stubs_phase: BooleanType? = UnspecifiedBoolean,
    target_embedded_compiler: BooleanType? = UnspecifiedBoolean,
    visibility: List<Label?>? = UnspecifiedList
) {
    val args = linkedSetOf<Argument>().also {
        it += Argument("name", Expression(name, ::StringLiteral))
        if (compile_phase !== UnspecifiedBoolean)
            it += Argument("compile_phase", Expression(compile_phase, ::BooleanLiteral))
        if (deps !== UnspecifiedList) it += Argument("deps", Expression(deps, ::ListExpression))
        if (id !== UnspecifiedString) it += Argument("id", Expression(id, ::StringLiteral))
        if (options !== UnspecifiedDictionary)
            it += Argument("options", Expression(options, ::DictionaryExpression))
        if (stubs_phase !== UnspecifiedBoolean)
            it += Argument("stubs_phase", Expression(stubs_phase, ::BooleanLiteral))
        if (target_embedded_compiler !== UnspecifiedBoolean)
            it += Argument(
                "target_embedded_compiler", Expression(target_embedded_compiler, ::BooleanLiteral)
            )
        if (visibility !== UnspecifiedList)
            it += Argument("visibility", Expression(visibility, ::ListExpression))
    }
    registerFunctionCallStatement("kt_compiler_plugin", args)
}

/**
 *
 */
fun BuildContext.kt_compiler_plugin(body: KtCompilerPluginContext.() -> Unit) =
    registerFunctionCallStatement("kt_compiler_plugin", KtCompilerPluginContext(), body)

/**
 *
 */
class KtCompilerPluginContext : FunctionCallContext() {
    var name: Name by fargs
    var compile_phase: BooleanType? by fargs
    var deps: List<Label?>? by fargs
    var id: StringType? by fargs
    var options: Map<Key, Value>? by fargs
    var stubs_phase: BooleanType? by fargs
    var target_embedded_compiler: BooleanType? by fargs
    var visibility: List<Label?>? by fargs
}

// ===== kt_jvm_binary =====

/**
 *
 */
fun BuildContext.kt_jvm_binary(
    name: Name,
    main_class: StringType,
    data: List<Label?>? = UnspecifiedList,
    deps: List<Label?>? = UnspecifiedList,
    jvm_flags: List<StringType?>? = UnspecifiedList,
    module_name: StringType? = UnspecifiedString,
    plugins: List<Label?>? = UnspecifiedList,
    resource_jars: List<Label?>? = UnspecifiedList,
    resource_strip_prefix: StringType? = UnspecifiedString,
    resources: List<Label?>? = UnspecifiedList,
    runtime_deps: List<Label?>? = UnspecifiedList,
    srcs: List<Label?>? = UnspecifiedList,
    visibility: List<Label?>? = UnspecifiedList
) {
    val args = linkedSetOf<Argument>().also {
        it += Argument("name", Expression(name, ::StringLiteral))
        it += Argument("main_class", Expression(main_class, ::StringLiteral))
        if (data !== UnspecifiedList) it += Argument("data", Expression(data, ::ListExpression))
        if (deps !== UnspecifiedList) it += Argument("deps", Expression(deps, ::ListExpression))
        if (jvm_flags !== UnspecifiedList)
            it += Argument("jvm_flags", Expression(jvm_flags, ::ListExpression))
        if (module_name !== UnspecifiedString)
            it += Argument("module_name", Expression(module_name, ::StringLiteral))
        if (plugins !== UnspecifiedList)
            it += Argument("plugins", Expression(plugins, ::ListExpression))
        if (resource_jars !== UnspecifiedList)
            it += Argument("resource_jars", Expression(resource_jars, ::ListExpression))
        if (resource_strip_prefix !== UnspecifiedString)
            it += Argument(
                "resource_strip_prefix", Expression(resource_strip_prefix, ::StringLiteral)
            )
        if (resources !== UnspecifiedList)
            it += Argument("resources", Expression(resources, ::ListExpression))
        if (runtime_deps !== UnspecifiedList)
            it += Argument("runtime_deps", Expression(runtime_deps, ::ListExpression))
        if (srcs !== UnspecifiedList) it += Argument("srcs", Expression(srcs, ::ListExpression))
        if (visibility !== UnspecifiedList)
            it += Argument("visibility", Expression(visibility, ::ListExpression))
    }
    registerFunctionCallStatement("kt_jvm_binary", args)
}

/**
 *
 */
fun BuildContext.kt_jvm_binary(body: KtJvmBinaryContext.() -> Unit) =
    registerFunctionCallStatement("kt_jvm_binary", KtJvmBinaryContext(), body)

/**
 *
 */
class KtJvmBinaryContext : FunctionCallContext() {
    var name: Name by fargs
    var data: List<Label?>? by fargs
    var deps: List<Label?>? by fargs
    var jvm_flags: List<StringType?>? by fargs
    var main_class: StringType by fargs
    var module_name: StringType? by fargs
    var plugins: List<Label?>? by fargs
    var resource_jars: List<Label?>? by fargs
    var resource_strip_prefix: StringType? by fargs
    var resources: List<Label?>? by fargs
    var runtime_deps: List<Label?>? by fargs
    var srcs: List<Label?>? by fargs
    var visibility: List<Label?>? by fargs
}

// ===== kt_jvm_import =====

/**
 *
 */
fun BuildContext.kt_jvm_import(
    name: Name,
    deps: List<Label?>? = UnspecifiedList,
    exported_compiler_plugins: List<Label?>? = UnspecifiedList,
    exports: List<Label?>? = UnspecifiedList,
    jar: Label? = UnspecifiedString,
    jars: List<Label?>? = UnspecifiedList,
    neverlink: BooleanType? = UnspecifiedBoolean,
    runtime_deps: List<Label?>? = UnspecifiedList,
    srcjar: Label? = UnspecifiedString,
    visibility: List<Label?>? = UnspecifiedList
) {
    val args = linkedSetOf<Argument>().also {
        it += Argument("name", Expression(name, ::StringLiteral))
        if (deps !== UnspecifiedList) it += Argument("deps", Expression(deps, ::ListExpression))
        if (exported_compiler_plugins !== UnspecifiedList)
            it += Argument(
                "exported_compiler_plugins",
                Expression(exported_compiler_plugins, ::ListExpression)
            )
        if (exports !== UnspecifiedList) it += Argument(
            "exports",
            Expression(exports, ::ListExpression)
        )
        if (jar !== UnspecifiedString) it += Argument("jar", Expression(jar, ::StringLiteral))
        if (jars !== UnspecifiedList) it += Argument("jars", Expression(jars, ::ListExpression))
        if (neverlink !== UnspecifiedBoolean) it += Argument(
            "neverlink",
            Expression(neverlink, ::BooleanLiteral)
        )
        if (runtime_deps !== UnspecifiedList) it += Argument(
            "runtime_deps",
            Expression(runtime_deps, ::ListExpression)
        )
        if (srcjar !== UnspecifiedString) it += Argument(
            "srcjar",
            Expression(srcjar, ::StringLiteral)
        )
        if (visibility !== UnspecifiedList) it += Argument(
            "visibility",
            Expression(visibility, ::ListExpression)
        )
    }
    registerFunctionCallStatement("kt_jvm_import", args)
}

/**
 *
 */
fun BuildContext.kt_jvm_import(body: KtJvmImportContext.() -> Unit) =
    registerFunctionCallStatement("kt_jvm_import", KtJvmImportContext(), body)

/**
 *
 */
class KtJvmImportContext : FunctionCallContext() {
    var name: Name by fargs
    var deps: List<Label?>? by fargs
    var exported_compiler_plugins: List<Label?>? by fargs
    var exports: List<Label?>? by fargs
    var jar: Label? by fargs
    var jars: List<Label?>? by fargs
    var neverlink: BooleanType? by fargs
    var runtime_deps: List<Label?>? by fargs
    var srcjar: Label? by fargs
    var visibility: List<Label?>? by fargs
}

// ===== kt_jvm_library =====

/**
 *
 */
fun BuildContext.kt_jvm_library(
    name: Name,
    data: List<Label?>? = UnspecifiedList,
    deps: List<Label?>? = UnspecifiedList,
    exported_compiler_plugins: List<Label?>? = UnspecifiedList,
    exports: List<Label?>? = UnspecifiedList,
    module_name: StringType? = UnspecifiedString,
    neverlink: BooleanType? = UnspecifiedBoolean,
    plugins: List<Label?>? = UnspecifiedList,
    resource_jars: List<Label?>? = UnspecifiedList,
    resource_strip_prefix: StringType? = UnspecifiedString,
    resources: List<Label?>? = UnspecifiedList,
    runtime_deps: List<Label?>? = UnspecifiedList,
    srcs: List<Label?>? = UnspecifiedList,
    visibility: List<Label?>? = UnspecifiedList
) {
    val args = linkedSetOf<Argument>().also {
        it += Argument("name", Expression(name, ::StringLiteral))
        if (data !== UnspecifiedList) it += Argument("data", Expression(data, ::ListExpression))
        if (deps !== UnspecifiedList) it += Argument("deps", Expression(deps, ::ListExpression))
        if (exported_compiler_plugins !== UnspecifiedList)
            it += Argument(
                "exported_compiler_plugins",
                Expression(exported_compiler_plugins, ::ListExpression)
            )
        if (exports !== UnspecifiedList) it += Argument(
            "exports",
            Expression(exports, ::ListExpression)
        )
        if (module_name !== UnspecifiedString) it += Argument(
            "module_name",
            Expression(module_name, ::StringLiteral)
        )
        if (neverlink !== UnspecifiedBoolean) it += Argument(
            "neverlink",
            Expression(neverlink, ::BooleanLiteral)
        )
        if (plugins !== UnspecifiedList) it += Argument(
            "plugins",
            Expression(plugins, ::ListExpression)
        )
        if (resource_jars !== UnspecifiedList)
            it += Argument("resource_jars", Expression(resource_jars, ::ListExpression))
        if (resource_strip_prefix !== UnspecifiedString)
            it += Argument(
                "resource_strip_prefix",
                Expression(resource_strip_prefix, ::StringLiteral)
            )
        if (resources !== UnspecifiedList) it += Argument(
            "resources",
            Expression(resources, ::ListExpression)
        )
        if (runtime_deps !== UnspecifiedList) it += Argument(
            "runtime_deps",
            Expression(runtime_deps, ::ListExpression)
        )
        if (srcs !== UnspecifiedList) it += Argument("srcs", Expression(srcs, ::ListExpression))
        if (visibility !== UnspecifiedList) it += Argument(
            "visibility",
            Expression(visibility, ::ListExpression)
        )
    }
    registerFunctionCallStatement("kt_jvm_library", args)
}

/**
 *
 */
fun BuildContext.kt_jvm_library(body: KtJvmLibraryContext.() -> Unit) =
    registerFunctionCallStatement("kt_jvm_library", KtJvmLibraryContext(), body)

/**
 *
 */
class KtJvmLibraryContext : FunctionCallContext() {
    var name: Name by fargs
    var data: List<Label?>? by fargs
    var deps: List<Label?>? by fargs
    var exported_compiler_plugins: List<Label?>? by fargs
    var exports: List<Label?>? by fargs
    var module_name: StringType? by fargs
    var neverlink: BooleanType? by fargs
    var plugins: List<Label?>? by fargs
    var resource_jars: List<Label?>? by fargs
    var resource_strip_prefix: StringType? by fargs
    var resources: List<Label?>? by fargs
    var runtime_deps: List<Label?>? by fargs
    var srcs: List<Label?>? by fargs
    var visibility: List<Label?>? by fargs
}

// ===== kt_android_library =====

/**
 *
 */
fun BuildContext.kt_android_library(
    name: Name,
    exports: List<Label?>? = UnspecifiedList,
    visibility: List<Label?>? = UnspecifiedList,
    srcs: List<Label?>? = UnspecifiedList,
    deps: List<Label?>? = UnspecifiedList,
    custom_package: StringType? = UnspecifiedString,
    manifest: Label? = UnspecifiedString,
    resource_files: List<Label?>? = UnspecifiedList,
    enable_data_binding: BooleanType? = UnspecifiedBoolean,
    exports_manifest: BooleanType? = UnspecifiedBoolean
) {
    val args = linkedSetOf<Argument>().also {
        it += Argument("name", Expression(name, ::StringLiteral))
        if (exports !== UnspecifiedList) it += Argument(
            "exports",
            Expression(exports, ::ListExpression)
        )
        if (srcs !== UnspecifiedList) it += Argument("srcs", Expression(srcs, ::ListExpression))
        if (custom_package !== UnspecifiedString)
            it += Argument("custom_package", Expression(custom_package, ::StringLiteral))
        if (manifest !== UnspecifiedString) it += Argument(
            "manifest",
            Expression(manifest, ::StringLiteral)
        )
        if (exports_manifest !== UnspecifiedBoolean)
            it += Argument("exports_manifest", Expression(exports_manifest, ::BooleanLiteral))
        if (enable_data_binding !== UnspecifiedBoolean)
            it += Argument("enable_data_binding", Expression(enable_data_binding, ::BooleanLiteral))
        if (deps !== UnspecifiedList) it += Argument("deps", Expression(deps, ::ListExpression))
        if (visibility !== UnspecifiedList) it += Argument(
            "visibility",
            Expression(visibility, ::ListExpression)
        )
        if (resource_files !== UnspecifiedList)
            it += Argument("resource_files", Expression(resource_files, ::ListExpression))
    }
    registerFunctionCallStatement("kt_android_library", args)
}

/**
 *
 */
fun BuildContext.kt_android_library(body: KtAndroidLibraryContext.() -> Unit) =
    registerFunctionCallStatement("kt_android_library", KtAndroidLibraryContext(), body)

/**
 *
 */
class KtAndroidLibraryContext : FunctionCallContext() {
    var name: Name by fargs
    var exports: List<Label?>? by fargs
    var visibility: List<Label?>? by fargs
    var srcs: List<Label?>? by fargs
    var deps: List<Label?>? by fargs
    var custom_package: StringType? by fargs
    var manifest: Label? by fargs
    var resource_files: List<Label?>? by fargs
    var enable_data_binding: BooleanType? by fargs
    var exports_manifest: BooleanType? by fargs
}

// ===== define_kt_toolchain =====

/**
 *
 */
fun WorkspaceContext.define_kt_toolchain(
    name: Name,
    api_version: StringType? = UnspecifiedString,
    jvm_target: StringType? = UnspecifiedString,
    language_version: StringType? = UnspecifiedString,
    experimental_use_abi_jars: BooleanType? = UnspecifiedBoolean,
    javac_options: Map<Key, Value>? = UnspecifiedDictionary,
    kotlinc_options: Map<Key, Value>? = UnspecifiedDictionary
) {
    val args = linkedSetOf<Argument>().also {
        it += Argument("name", Expression(name, ::StringLiteral))
        if (api_version !== UnspecifiedString) it += Argument(
            "api_version",
            Expression(name, ::StringLiteral)
        )
        if (jvm_target !== UnspecifiedString) it += Argument(
            "jvm_target",
            Expression(jvm_target, ::StringLiteral)
        )
        if (language_version !== UnspecifiedString)
            it += Argument("language_version", Expression(language_version, ::StringLiteral))
        if (experimental_use_abi_jars !== UnspecifiedBoolean)
            it += Argument(
                "experimental_use_abi_jars",
                Expression(experimental_use_abi_jars, ::BooleanLiteral)
            )
        if (javac_options !== UnspecifiedDictionary)
            it += Argument("javac_options", Expression(javac_options, ::DictionaryExpression))
        if (kotlinc_options !== UnspecifiedDictionary)
            it += Argument("kotlinc_options", Expression(kotlinc_options, ::DictionaryExpression))
    }
    registerFunctionCallStatement("define_kt_toolchain", args)
}

/**
 *
 */
fun WorkspaceContext.define_kt_toolchain(body: DefineKtToolchainContext.() -> Unit) =
    registerFunctionCallStatement("define_kt_toolchain", DefineKtToolchainContext(), body)

/**
 *
 */
class DefineKtToolchainContext : FunctionCallContext() {
    var name: Name by fargs
    var api_version: StringType? by fargs
    var jvm_target: StringType? by fargs
    var language_version: StringType? by fargs
    var experimental_use_abi_jars: BooleanType? by fargs
    var javac_options: Map<Key, Value>? by fargs
    var kotlinc_options: Map<Key, Value>? by fargs
}

// ===== kt_register_toolchains =====

/**
 *
 */
fun WorkspaceContext.kt_register_toolchains() =
    registerFunctionCallStatement("kt_register_toolchains")

// ===== kotlin_repositories =====

fun WorkspaceContext.kotlin_repositories(
    compiler_release: Map<Key, Value>? = UnspecifiedDictionary
) {
    val args = linkedSetOf<Argument>().also {
        if (compiler_release !== UnspecifiedDictionary)
            it += Argument("compiler_release", Expression(compiler_release, ::DictionaryExpression))
    }
    registerFunctionCallStatement("kotlin_repositories", args)
}

/**
 *
 */
fun WorkspaceContext.kotlin_repositories(body: KotlinRepositoriesContext.() -> Unit) =
    registerFunctionCallStatement("kotlin_repositories", KotlinRepositoriesContext(), body)

/**
 *
 */
class KotlinRepositoriesContext : FunctionCallContext() {
    var compiler_release: StringType by fargs
}
