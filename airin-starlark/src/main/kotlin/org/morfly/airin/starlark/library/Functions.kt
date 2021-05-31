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

package org.morfly.airin.starlark.library

import org.morfly.airin.starlark.elements.*
import org.morfly.airin.starlark.lang.*
import org.morfly.airin.starlark.lang.feature.DictionaryContext
import org.morfly.airin.starlark.lang.feature.functionCallExpression
import org.morfly.airin.starlark.lang.feature.listFunctionCall
import org.morfly.airin.starlark.lang.feature.registerFunctionCallStatement


/**
 *
 */
fun BaseStarlarkContext<*>.glob(
    include: List<Label?>,
    exclude: List<Label>? = UnspecifiedList,
    exclude_directories: IntegerType? = UnspecifiedInteger,
    allow_empty: BooleanType? = UnspecifiedBoolean
): List<Label> {
    val args = linkedSetOf<Argument>().also {
        it += Argument("", Expression(include, ::ListExpression))
        if (exclude !== UnspecifiedList) it += Argument("exclude", Expression(exclude, ::ListExpression))
        if (exclude_directories !== UnspecifiedInteger)
            it += Argument("exclude_directories", Expression(exclude_directories, ::IntegerLiteral))
        if (allow_empty !== UnspecifiedBoolean) it += Argument("allow_empty", Expression(allow_empty, ::BooleanLiteral))
    }
    return listFunctionCall("glob", args)
}

/**
 *
 */
fun BaseStarlarkContext<*>.glob(
    vararg include: Label?,
    exclude: List<Label>? = UnspecifiedList,
    exclude_directories: IntegerType? = UnspecifiedInteger,
    allow_empty: BooleanType? = UnspecifiedBoolean
): List<Label> = glob(
    include.toList(),
    exclude,
    exclude_directories,
    allow_empty
)

/**
 *
 */
fun BaseStarlarkContext<*>.`package`(
    default_visibility: List<Label>? = UnspecifiedList,
    default_deprecation: StringType? = UnspecifiedString,
    default_testonly: BooleanType? = UnspecifiedBoolean,
    features: List<StringType>? = UnspecifiedList
) {
    val args = linkedSetOf<Argument>().also {
        if (default_visibility !== UnspecifiedList)
            it += Argument("default_visibility", Expression(default_visibility, ::ListExpression))
        if (default_deprecation !== UnspecifiedString)
            it += Argument("default_deprecation", Expression(default_deprecation, ::StringLiteral))
        if (default_testonly !== UnspecifiedBoolean)
            it += Argument("default_testonly", Expression(default_testonly, ::BooleanLiteral))
        if (features !== UnspecifiedList)
            it += Argument("features", Expression(features, ::ListExpression))
    }
    registerFunctionCallStatement(name = "package", args)
}

/**
 *
 */
fun BaseStarlarkContext<*>.exports_files(
    exports_files: List<Label>,
    visibility: List<Label>? = UnspecifiedList,
    licences: List<StringType>? = UnspecifiedList
) {
    val args = linkedSetOf<Argument>().also {
        it += Argument("", Expression(exports_files, ::ListExpression))
        if (visibility !== UnspecifiedList)
            it += Argument("visibility", Expression(visibility, ::ListExpression))
        if (licences !== UnspecifiedList)
            it += Argument("licences", Expression(licences, ::ListExpression))
    }
    registerFunctionCallStatement(name = "exports_files", args)
}

/**
 *
 */
fun BaseStarlarkContext<*>.exports_files(
    vararg exports_files: Label,
    visibility: List<Label>? = UnspecifiedList,
    licences: List<StringType>? = UnspecifiedList
) {
    exports_files(
        exports_files.toList(),
        visibility,
        licences
    )
}


/**
 *
 */
inline fun <reified T> BaseStarlarkContext<*>.select(
    select: Map<Key, Value>,
    no_match_error: StringType? = UnspecifiedString
): T {
    val args = linkedSetOf<Argument>().also {
        it += Argument("", Expression(select, ::DictionaryExpression))
        if (no_match_error !== UnspecifiedString)
            it += Argument("no_match_error", Expression(no_match_error, ::StringLiteral))
    }
    return functionCallExpression("select", args)
}

/**
 *
 */
inline fun <reified T> BaseStarlarkContext<*>.select(
    select: DictionaryContext.() -> Unit,
    no_match_error: StringType? = UnspecifiedString
): T {
    val args = linkedSetOf<Argument>().also {
        it += Argument("", DictionaryExpression(DictionaryContext().apply(select).kwargs))
        if (no_match_error !== UnspecifiedString)
            it += Argument("no_match_error", Expression(no_match_error, ::StringLiteral))
    }
    return functionCallExpression("select", args)
}


fun BaseStarlarkContext<*>.test() {
    val list: List<Label> = select(dict {})
    val list1: List<Label> = select({})
    val string: StringType = select(dict {})
    val string1: StringType = select({})

    val s: Map<Key, Value> = dict {} `+` dict {}
}