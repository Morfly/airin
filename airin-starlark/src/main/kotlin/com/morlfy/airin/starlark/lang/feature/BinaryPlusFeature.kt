@file:Suppress("FunctionName")

package com.morlfy.airin.starlark.lang.feature

import com.morlfy.airin.starlark.elements.*
import com.morlfy.airin.starlark.elements.BinaryOperator.PLUS
import com.morlfy.airin.starlark.lang.Key
import com.morlfy.airin.starlark.lang.StringType
import com.morlfy.airin.starlark.lang.Value


/**
 *
 */
internal interface BinaryPlusFeature : LanguageFeature {

    /**
     *
     */
    infix fun StringType?.`+`(other: StringType?): StringType =
        StringBinaryOperation(
            left = Expression(this),
            operator = PLUS,
            right = other?.let(::StringLiteral)
        )

    /**
     *
     */
    infix fun <T> List<T>?.`+`(other: List<T>?): List<T> =
        ListBinaryOperation(
            left = Expression(this),
            operator = PLUS,
            right = other?.let(::ListExpression)
        )

    /**
     *
     */
    infix fun Map<*, Value>?.`+`(other: Map<*, Value>?): Map<Key, Value> =
        DictionaryBinaryOperation(
            left = Expression(this),
            operator = PLUS,
            right = other?.let(::DictionaryExpression)
        )

    /**
     *
     */
    infix fun Map<*, Value>?.`+`(body: DictionaryContext.() -> Unit): Map<Key, Value> =
        DictionaryBinaryOperation(
            left = Expression(this),
            operator = PLUS,
            right = DictionaryExpression(DictionaryContext().apply(body).kwargs)
        )
}