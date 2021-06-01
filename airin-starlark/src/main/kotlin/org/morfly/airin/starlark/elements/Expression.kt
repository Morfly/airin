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

package org.morfly.airin.starlark.elements

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract


/**
 * Base type for all expression elements in a syntax tree.
 *
 * Possible expression types:
 *  null - as value of Expression? type corresponds to the 'None' type from Starlark,
 *  [StringLiteral], [IntegerLiteral], [FloatLiteral], [BooleanLiteral],
 *  [ListExpression],
 *  [DictionaryExpression],
 *  [TupleExpression],
 *  [BinaryOperation],
 *  [ListComprehension],
 *  [DictionaryComprehension],
 *  [FunctionCall],
 *  [Reference],
 *  [SliceExpression],
 *  [DynamicValue],
 *  [RawText]: TODO.
 */
sealed interface Expression : Element

/**
 * Converts a Kotlin type to a corresponding Starlark expression element.
 *
 * In case the input value does not correspond to any Starlark element it will be converted [toString] and treated like
 * a [StringLiteral].
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

/**
 * Creates an expression element of specific a specific type only in case the argument is not already an expression.
 */
inline fun <T : Any> Expression(value: T?, builder: (value: T) -> Expression): Expression? =
    when (value) {
        null -> null
        is Expression -> value
        else -> builder(value)
    }