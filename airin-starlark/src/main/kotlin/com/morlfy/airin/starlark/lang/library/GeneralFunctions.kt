package com.morlfy.airin.starlark.lang.library

import com.morlfy.airin.starlark.lang.*
import com.morlfy.airin.starlark.lang.feature.DictionaryContext
import com.morlfy.airin.starlark.lang.feature.functionCallExpression
import com.morlfy.airin.starlark.lang.feature.listFunctionCall
import com.morlfy.airin.starlark.lang.feature.registerFunctionCallStatement


/**
 *
 */
fun StarlarkContext.glob(
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
fun StarlarkContext.glob(
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
fun StarlarkContext.`package`(
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
fun StarlarkContext.exports_files(
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
fun StarlarkContext.exports_files(
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
inline fun <reified T> StarlarkContext.select(
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
inline fun <reified T> StarlarkContext.select(
    select: DictionaryContext.() -> Unit,
    no_match_error: StringType? = null
): T = functionCallExpression(
    name = "select",
    args = mapOf(
        "" to DictionaryContext().apply(select).kwargs,
        "no_match_error" to no_match_error
    )
)

fun StarlarkContext.test() {
    val list: List<Label> = select(dict {})
    val list1: List<Label> = select({})
    val string: StringType = select(dict {})
    val string1: StringType = select({})

    val s: Map<Key, Value> = dict {} `+` dict {}
}