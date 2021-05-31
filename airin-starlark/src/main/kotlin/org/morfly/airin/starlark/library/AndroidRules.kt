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

@file:Suppress("SpellCheckingInspection", "FunctionName", "PropertyName")

package org.morfly.airin.starlark.library

import org.morfly.airin.starlark.elements.*
import org.morfly.airin.starlark.lang.*
import org.morfly.airin.starlark.lang.feature.FunctionCallContext
import org.morfly.airin.starlark.lang.feature.registerFunctionCallStatement


// ===== android_library =====

/**
 *
 */
fun BuildContext.android_library(
    name: Name,
    custom_package: StringType? = UnspecifiedString,
    manifest: Label? = UnspecifiedString,
    exports_manifest: BooleanType? = UnspecifiedBoolean,
    resource_files: List<Label?>? = UnspecifiedList,
    srcs: List<Label?>? = UnspecifiedList,
    enable_data_binding: BooleanType? = UnspecifiedBoolean,
    plugins: List<Label?>? = UnspecifiedList,
    deps: List<Label?>? = UnspecifiedList,
    visibility: List<Label?>? = UnspecifiedList
) {
    val args = linkedSetOf<Argument>().also {
        it += Argument("name", Expression(name, ::StringLiteral))
        if (srcs !== UnspecifiedList) it += Argument("srcs", Expression(srcs, ::ListExpression))
        if (custom_package !== UnspecifiedString)
            it += Argument("custom_package", Expression(custom_package, ::StringLiteral))
        if (manifest !== UnspecifiedString) it += Argument("manifest", Expression(manifest, ::StringLiteral))
        if (exports_manifest !== UnspecifiedBoolean)
            it += Argument("exports_manifest", Expression(exports_manifest, ::BooleanLiteral))
        if (resource_files !== UnspecifiedList)
            it += Argument("resource_files", Expression(resource_files, ::ListExpression))
        if (enable_data_binding !== UnspecifiedBoolean)
            it += Argument("enable_data_binding", Expression(enable_data_binding, ::BooleanLiteral))
        if (plugins !== UnspecifiedList) it += Argument("plugins", Expression(plugins, ::ListExpression))
        if (deps !== UnspecifiedList) it += Argument("deps", Expression(deps, ::ListExpression))
        if (visibility !== UnspecifiedList) it += Argument("visibility", Expression(visibility, ::ListExpression))
    }
    registerFunctionCallStatement("android_library", args)
}

/**
 *
 */
fun BuildContext.android_library(body: AndroidLibraryContext.() -> Unit) =
    registerFunctionCallStatement("android_library", AndroidLibraryContext(), body)

/**
 *
 */
class AndroidLibraryContext : FunctionCallContext() {
    var name: Name by fargs
    var custom_package: StringType? by fargs
    var manifest: Label? by fargs
    var exports_manifest: BooleanType? by fargs
    var resource_files: List<Label?>? by fargs
    var srcs: List<Label?>? by fargs
    var enable_data_binding: BooleanType? by fargs
    var plugins: List<Label?>? by fargs
    var deps: List<Label?>? by fargs
    var visibility: List<Label?>? by fargs
}

// ===== android_binary =====

/**
 *
 */
fun BuildContext.android_binary(
    name: Name,
    custom_package: StringType? = UnspecifiedString,
    manifest: Label? = UnspecifiedString,
    manifest_values: Map<Key, Value>? = UnspecifiedDictionary,
    debug_key: Label? = UnspecifiedString,
    enable_data_binding: BooleanType? = UnspecifiedBoolean,
    multidex: StringType? = UnspecifiedString,
    incremental_dexing: IntegerType? = UnspecifiedInteger,
    crunch_png: BooleanType? = UnspecifiedBoolean,
    dex_shards: IntegerType? = UnspecifiedInteger,
    resource_files: List<Label?>? = UnspecifiedList,
    srcs: List<Label?>? = UnspecifiedList,
    plugins: List<Label?>? = UnspecifiedList,
    deps: List<Label?>? = UnspecifiedList,
    visibility: List<Label?>? = UnspecifiedList,
    args: List<StringType>? = UnspecifiedList,
    env: Map<Key, Value>? = UnspecifiedDictionary,
    output_licenses: List<StringType>? = UnspecifiedList,
) {
    val fargs = linkedSetOf<Argument>().also {
        it += Argument("name", Expression(name, ::StringLiteral))
        if (custom_package !== UnspecifiedString)
            it += Argument("custom_package", Expression(custom_package, ::StringLiteral))
        if (manifest !== UnspecifiedString) it += Argument("manifest", Expression(manifest, ::StringLiteral))
        if (manifest_values !== UnspecifiedDictionary)
            it += Argument("manifest_values", Expression(manifest_values, ::DictionaryExpression))
        if (debug_key !== UnspecifiedString) it += Argument("debug_key", Expression(debug_key, ::StringLiteral))
        if (enable_data_binding !== UnspecifiedBoolean)
            it += Argument("enable_data_binding", Expression(enable_data_binding, ::BooleanLiteral))
        if (multidex !== UnspecifiedString) it += Argument("multidex", Expression(multidex, ::StringLiteral))
        if (incremental_dexing !== UnspecifiedInteger)
            it += Argument("incremental_dexing", Expression(incremental_dexing, ::IntegerLiteral))
        if (crunch_png !== UnspecifiedBoolean) it += Argument("crunch_png", Expression(crunch_png, ::BooleanLiteral))
        if (dex_shards !== UnspecifiedInteger) it += Argument("dex_shards", Expression(dex_shards, ::IntegerLiteral))
        if (resource_files !== UnspecifiedList)
            it += Argument("resource_files", Expression(resource_files, ::ListExpression))
        if (srcs !== UnspecifiedList) it += Argument("srcs", Expression(srcs, ::ListExpression))
        if (plugins !== UnspecifiedList) it += Argument("plugins", Expression(plugins, ::ListExpression))
        if (deps !== UnspecifiedList) it += Argument("deps", Expression(deps, ::ListExpression))
        if (visibility !== UnspecifiedList) it += Argument("visibility", Expression(visibility, ::ListExpression))
        if (args !== UnspecifiedList) it += Argument("args", Expression(args, ::ListExpression))
        if (env !== UnspecifiedDictionary) it += Argument("env", Expression(env, ::DictionaryExpression))
        if (output_licenses !== UnspecifiedList)
            it += Argument("output_licenses", Expression(output_licenses, ::ListExpression))
    }
    registerFunctionCallStatement("android_binary", args = fargs)
}

/**
 *
 */
fun BuildContext.android_binary(body: AndroidBinaryContext.() -> Unit) =
    registerFunctionCallStatement("android_binary", AndroidBinaryContext(), body)

/**
 *
 */
class AndroidBinaryContext : FunctionCallContext() {
    var name: Name by fargs
    var custom_package: StringType? by fargs
    var manifest: Label? by fargs
    var manifest_values: Map<Key, Value>? by fargs
    var debug_key: Label? by fargs
    var enable_data_binding: BooleanType? by fargs
    var multidex: StringType? by fargs
    var incremental_dexing: IntegerType? by fargs
    var crunch_png: BooleanType? by fargs
    var dex_shards: IntegerType? by fargs
    var resource_files: List<Label?>? by fargs
    var srcs: List<Label?>? by fargs
    var plugins: List<Label?>? by fargs
    var deps: List<Label?>? by fargs
    var visibility: List<Label?>? by fargs
    var args: List<StringType>? by fargs
    var env: Map<Key, Value>? by fargs
    var output_licenses: List<StringType>? by fargs
}

// ===== aar_import =====

fun BuildContext.aar_import(
    name: Name,
    aar: Label = UnspecifiedString,
    exports: List<Label?>? = UnspecifiedList,
    srcjar: Label? = UnspecifiedString,
    deps: List<Label?>? = UnspecifiedList,
    visibility: List<Label?>? = UnspecifiedList
) {
    val args = linkedSetOf<Argument>().also {
        it += Argument("name", Expression(name, ::StringLiteral))
        if (aar !== UnspecifiedString) it += Argument("aar", Expression(aar, ::StringLiteral))
        if (exports !== UnspecifiedList) it += Argument("exports", Expression(exports, ::ListExpression))
        if (srcjar !== UnspecifiedString) it += Argument("srcjar", Expression(srcjar, ::StringLiteral))
        if (deps !== UnspecifiedList) it += Argument("deps", Expression(deps, ::ListExpression))
        if (visibility !== UnspecifiedList) it += Argument("visibility", Expression(visibility, ::ListExpression))
    }
    registerFunctionCallStatement("aar_import", args)
}

/**
 *
 */
fun BuildContext.aar_import(body: AarImportContext.() -> Unit) =
    registerFunctionCallStatement("aar_import", AarImportContext(), body)

/**
 *
 */
class AarImportContext : FunctionCallContext() {
    var name: Name by fargs
    var aar: Label by fargs
    var exports: List<Label?>? by fargs
    var srcjar: Label? by fargs
    var deps: List<Label?>? by fargs
    var visibility: List<Label?>? by fargs
}

// ===== android_sdk_repository =====

/**
 *
 */
fun WorkspaceContext.android_sdk_repository(
    name: Name,
    api_level: IntegerType? = UnspecifiedInteger,
    build_tools_version: StringType? = UnspecifiedString,
    path: StringType? = UnspecifiedString,
    repo_mapping: Map<Key, Value>? = UnspecifiedDictionary
) {
    val args = linkedSetOf<Argument>().also {
        it += Argument("name", Expression(name, ::StringLiteral))
        if (api_level !== UnspecifiedInteger) it += Argument("api_level", Expression(api_level, ::IntegerLiteral))
        if (build_tools_version !== UnspecifiedString)
            it += Argument("build_tools_version", Expression(build_tools_version, ::StringLiteral))
        if (path !== UnspecifiedString) it += Argument("path", Expression(path, ::StringLiteral))
        if (repo_mapping !== UnspecifiedDictionary)
            it += Argument("repo_mapping", Expression(repo_mapping, ::DictionaryExpression))
    }
    registerFunctionCallStatement("android_sdk_repository", args)
}

/**
 *
 */
fun WorkspaceContext.android_sdk_repository(body: AndroidSdkRepositoryContext.() -> Unit) =
    registerFunctionCallStatement("android_sdk_repository", AndroidSdkRepositoryContext(), body)

/**
 *
 */
class AndroidSdkRepositoryContext : FunctionCallContext() {
    var name: Name by fargs
    var api_level: IntegerType? by fargs
    var build_tools_version: StringType? by fargs
    var path: StringType? by fargs
    var repo_mapping: Map<Key, Value>? by fargs
}

// ===== android_ndk_repository =====

/**
 *
 */
fun WorkspaceContext.android_ndk_repository(
    name: Name,
    api_level: IntegerType? = UnspecifiedInteger,
    path: StringType? = UnspecifiedString,
    repo_mapping: Map<Key, Value>? = UnspecifiedDictionary
) {
    val args = linkedSetOf<Argument>().also {
        it += Argument("name", Expression(name, ::StringLiteral))
        if (api_level !== UnspecifiedInteger) it += Argument("api_level", Expression(api_level, ::IntegerLiteral))
        if (path !== UnspecifiedString) it += Argument("path", Expression("path", ::StringLiteral))
        if (repo_mapping !== UnspecifiedDictionary)
            it += Argument("repo_mapping", Expression(repo_mapping, ::DictionaryExpression))
    }
    registerFunctionCallStatement("android_ndk_repository", args)
}

/**
 *
 */
fun WorkspaceContext.android_ndk_repository(body: AndroidNdkRepositoryContext.() -> Unit) =
    registerFunctionCallStatement("android_ndk_repository", AndroidNdkRepositoryContext(), body)

/**
 *
 */
class AndroidNdkRepositoryContext : FunctionCallContext() {
    var name: Name by fargs
    var api_level: Int? by fargs
    var path: CharSequence? by fargs
    var repo_mapping: Map<Key, Value>? by fargs
}