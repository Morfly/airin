@file:Suppress("FunctionName")

package com.morlfy.airin.starlark.elements


/**
 * Base type for expression elements.
 *
 * Possible expression types:
 *  null - as value of [Expression?] type corresponds to starlark 'None' type,
 *  [Literal]: [IntegerLiteral], [FloatLiteral], [BooleanLiteral],
 *  [ListExpression],
 *  [DictionaryExpression],
 *  [BinaryOperation]: [StringBinaryOperation], [ListBinaryOperation], [DictionaryBinaryOperation], [AnyBinaryOperation],
 *  [Comprehension]: TODO [ListComprehension], [DictionaryComprehension],
 *  [FunctionCall]: [StringFunctionCall], [ListFunctionCall], [DictionaryFunctionCall], [AnyFunctionCall],
 *  [Reference]: [StringReference], [ListReference], [DictionaryReference], [AnyReference],
 *  [RawText]: TODO.
 */
sealed interface Expression : Element

// TODO
sealed interface StringExpression: Expression
//sealed interface ListExpression<T>: Expression

/**
 * Converts base kotlin types to corresponding starlark expression elements.
 */
fun Expression(value: Any?): Expression? =
    when (value) {
        null -> null
        is Expression -> value
        is String -> StringLiteral(value)
        is Boolean -> BooleanLiteral(value)
        is List<*> -> ListExpression(value)
        is Map<*, *> -> DictionaryExpression(value)
        is Int -> IntegerLiteral(value.toLong())
        is Double -> FloatLiteral(value)
        is Float -> FloatLiteral(value.toDouble())
        is Long -> IntegerLiteral(value)
        is Byte -> IntegerLiteral(value.toLong())
        is Short -> IntegerLiteral(value.toLong())
        else -> StringLiteral(value.toString())
    }