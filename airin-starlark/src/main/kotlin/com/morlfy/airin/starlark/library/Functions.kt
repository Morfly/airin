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

package com.morlfy.airin.starlark.library

import com.morlfy.airin.starlark.lang.*
import com.morlfy.airin.starlark.lang.feature.DictionaryContext
import com.morlfy.airin.starlark.lang.feature.functionCallExpression
import com.morlfy.airin.starlark.lang.feature.listFunctionCall
import com.morlfy.airin.starlark.lang.feature.registerFunctionCallStatement


/**
 *
 */
fun BaseStarlarkContext.glob(
    include: List<Label?>,
    exclude: List<Label>? = UnspecifiedList,
    exclude_directories: IntegerType? = UnspecifiedInteger,
    allow_empty: BooleanType? = UnspecifiedBoolean
): List<Label> = listFunctionCall(
    name = "glob",
    args = mapOf(
        "" to include,
        "exclude" to exclude,
        "exclude_directories" to exclude_directories,
        "allow_empty" to allow_empty
    )
)

/**
 *
 */
fun BaseStarlarkContext.glob(
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
fun BaseStarlarkContext.`package`(
    default_visibility: List<Label>? = UnspecifiedList,
    default_deprecation: StringType? = UnspecifiedString,
    default_testonly: BooleanType? = UnspecifiedBoolean,
    features: List<StringType>? = UnspecifiedList
) {
    registerFunctionCallStatement(
        name = "package",
        args = mapOf(
            "default_visibility" to default_visibility,
            "default_deprecation" to default_deprecation,
            "default_testonly" to default_testonly,
            "features" to features
        )
    )
}

/**
 *
 */
fun BaseStarlarkContext.exports_files(
    exports_files: List<Label>,
    visibility: List<Label>? = UnspecifiedList,
    licences: List<StringType>? = UnspecifiedList
) {
    registerFunctionCallStatement(
        name = "exports_files",
        args = mapOf(
            "" to exports_files,
            "visibility" to visibility,
            "licences" to licences,
        )
    )
}

/**
 *
 */
fun BaseStarlarkContext.exports_files(
    vararg exports_files: Label,
    visibility: List<Label>? = null,
    licences: List<StringType>? = null
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
inline fun <reified T> BaseStarlarkContext.select(
    select: Map<Key, Value>,
    no_match_error: StringType? = null
): T = functionCallExpression(
    name = "select",
    args = mapOf(
        "" to select,
        "no_match_error" to no_match_error
    )
)

/**
 *
 */
inline fun <reified T> BaseStarlarkContext.select(
    select: DictionaryContext.() -> Unit,
    no_match_error: StringType? = null
): T = functionCallExpression(
    name = "select",
    args = mapOf(
        "" to DictionaryContext().apply(select).kwargs,
        "no_match_error" to no_match_error
    )
)

fun BaseStarlarkContext.test() {
    val list: List<Label> = select(dict {})
    val list1: List<Label> = select({})
    val string: StringType = select(dict {})
    val string1: StringType = select({})

    val s: Map<Key, Value> = dict {} `+` dict {}
}