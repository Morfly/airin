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

@file:Suppress("SpellCheckingInspection", "PropertyName")

package org.morfly.airin.starlark.library

import org.morfly.airin.starlark.elements.*
import org.morfly.airin.starlark.lang.*
import org.morfly.airin.starlark.lang.feature.FunctionCallContext
import org.morfly.airin.starlark.lang.feature.registerFunctionCallStatement


// ===== java_library =====

/**
 *
 */
fun BuildContext.java_library(
    name: Name,
    srcs: List<Label?>? = UnspecifiedList,
    resources: List<Label?>? = UnspecifiedList,
    exports: List<Label?>? = UnspecifiedList,
    plugins: List<Label?>? = UnspecifiedList,
    deps: List<Label?>? = UnspecifiedList,
    visibility: List<Label?>? = UnspecifiedList,
) {
    val args = linkedSetOf<Argument>().also {
        it += Argument("name", Expression(name, ::StringLiteral))
        if (srcs !== UnspecifiedList) it += Argument("srcs", Expression(srcs, ::ListExpression))
        if (resources !== UnspecifiedList) it += Argument("resources", Expression(resources, ::ListExpression))
        if (exports !== UnspecifiedList) it += Argument("exports", Expression(exports, ::ListExpression))
        if (plugins !== UnspecifiedList) it += Argument("plugins", Expression(plugins, ::ListExpression))
        if (deps !== UnspecifiedList) it += Argument("deps", Expression(deps, ::ListExpression))
        if (visibility !== UnspecifiedList) it += Argument("visibility", Expression(visibility, ::ListExpression))
    }
    registerFunctionCallStatement(name = "java_library", args)
}

/**
 *
 */
fun BuildContext.java_library(body: JavaLibraryContext.() -> Unit) =
    registerFunctionCallStatement("java_library", JavaLibraryContext(), body)

/**
 *
 */
class JavaLibraryContext : FunctionCallContext() {
    var name: Name by fargs
    var srcs: List<Label?>? by fargs
    var resources: List<Label?>? by fargs
    var exports: List<Label?>? by fargs
    var plugins: List<Label?>? by fargs
    var deps: List<Label?>? by fargs
    var visibility: List<Label?>? by fargs
}

// ===== java_binary =====

/**
 *
 */
fun BuildContext.java_binary(
    name: Name,
    srcs: List<Label?>? = UnspecifiedList,
    resources: List<Label?>? = UnspecifiedList,
    exports: List<Label?>? = UnspecifiedList,
    plugins: List<Label?>? = UnspecifiedList,
    main_class: StringType? = UnspecifiedString,
    deps: List<Label?>? = UnspecifiedList,
    visibility: List<Label?>? = UnspecifiedList,
    args: List<StringType?>? = UnspecifiedList,
    env: Map<Key, Value>? = UnspecifiedDictionary,
    output_licenses: List<StringType?>? = UnspecifiedList,
) {
    val fargs = linkedSetOf<Argument>().also {
        it += Argument("name", Expression(name, ::StringLiteral))
        if (srcs !== UnspecifiedList) it += Argument("srcs", Expression(srcs, ::ListExpression))
        if (resources !== UnspecifiedList) it += Argument("resources", Expression(resources, ::ListExpression))
        if (exports !== UnspecifiedList) it += Argument("exports", Expression(exports, ::ListExpression))
        if (plugins !== UnspecifiedList) it += Argument("plugins", Expression(plugins, ::ListExpression))
        if (main_class !== UnspecifiedString) it += Argument("main_class", Expression(main_class, ::StringLiteral))
        if (deps !== UnspecifiedList) it += Argument("deps", Expression(deps, ::ListExpression))
        if (visibility !== UnspecifiedList) it += Argument("visibility", Expression(visibility, ::ListExpression))
        if (args !== UnspecifiedList) it += Argument("args", Expression(args, ::ListExpression))
        if (env !== UnspecifiedDictionary) it += Argument("env", Expression(env, ::DictionaryExpression))
        if (output_licenses !== UnspecifiedList) it += Argument(
            "output_licenses", Expression(output_licenses, ::ListExpression)
        )
    }
    registerFunctionCallStatement("java_binary", fargs)
}

/**
 *
 */
fun BuildContext.java_binary(body: JavaBinaryContext.() -> Unit) =
    registerFunctionCallStatement("java_binary", JavaBinaryContext(), body)

/**
 *
 */
class JavaBinaryContext : FunctionCallContext() {
    var name: Name by fargs
    var srcs: List<Label?>? by fargs
    var resources: List<Label?>? by fargs
    var exports: List<Label?>? by fargs
    var plugins: List<Label?>? by fargs
    var main_class: StringType? by fargs
    var deps: List<Label?>? by fargs
    var visibility: List<Label?>? by fargs
    var args: List<StringType?>? by fargs
    var env: Map<String, StringType?>? by fargs
    var output_licenses: List<StringType?>? by fargs
}

// ===== java_import =====

/**
 *
 */
fun BuildContext.java_import(
    name: Name,
    jars: List<Label?>? = UnspecifiedList,
    exports: List<Label?>? = UnspecifiedList,
    deps: List<Label?>? = UnspecifiedList,
    visibility: List<Label?>? = UnspecifiedList,
    neverlink: BooleanType? = UnspecifiedBoolean
) {
    val args = linkedSetOf<Argument>().also {
        it += Argument("name", Expression(name, ::StringLiteral))
        if (jars !== UnspecifiedList) it += Argument("jars", Expression(jars, ::ListExpression))
        if (exports !== UnspecifiedList) it += Argument("exports", Expression(exports, ::ListExpression))
        if (deps !== UnspecifiedList) it += Argument("deps", Expression(deps, ::ListExpression))
        if (visibility !== UnspecifiedList) it += Argument("visibility", Expression(visibility, ::ListExpression))
        if (neverlink !== UnspecifiedBoolean) it += Argument("neverlink", Expression(neverlink, ::BooleanLiteral))
    }
    registerFunctionCallStatement("java_import", args)
}

/**
 *
 */
fun BuildContext.java_import(body: JavaImportContext.() -> Unit) =
    registerFunctionCallStatement("java_import", JavaImportContext(), body)

/**
 *
 */
class JavaImportContext : FunctionCallContext() {
    var name: Name by fargs
    var jars: List<Label?>? by fargs
    var exports: List<Label?>? by fargs

    var deps: List<Label?>? by fargs
    var visibility: List<Label?>? by fargs
    var neverlink: BooleanType? by fargs
}

// ===== java_plugin =====

/**
 *
 */
fun BuildContext.java_plugin(
    name: Name,
    processor_class: StringType? = UnspecifiedString,
    generates_api: BooleanType? = UnspecifiedBoolean,
    deps: List<Label?>? = UnspecifiedList,
    visibility: List<Label?>? = UnspecifiedList
) {
    val args = linkedSetOf<Argument>().also {
        it += Argument("name", Expression(name, ::StringLiteral))
        if (processor_class !== UnspecifiedString)
            it += Argument("processor_class", Expression(processor_class, ::StringLiteral))
        if (generates_api !== UnspecifiedBoolean)
            it += Argument("generates_api", Expression(generates_api, ::BooleanLiteral))
        if (deps !== UnspecifiedList) it += Argument("deps", Expression(deps, ::ListExpression))
        if (visibility !== UnspecifiedList) it += Argument("visibility", Expression(visibility, ::ListExpression))
    }
    registerFunctionCallStatement("java_plugin", args)
}

/**
 *
 */
fun BuildContext.java_plugin(body: JavaPluginContext.() -> Unit) =
    registerFunctionCallStatement("java_plugin", JavaPluginContext(), body)

/**
 *
 */
class JavaPluginContext : FunctionCallContext() {
    var name: Name by fargs
    var processor_class: StringType? by fargs
    var generates_api: BooleanType? by fargs
    var deps: List<Label?>? by fargs
    var visibility: List<Label?>? by fargs
}