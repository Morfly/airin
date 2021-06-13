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

package org.morfly.airin.starlark.lang.feature

import org.morfly.airin.starlark.elements.*
import org.morfly.airin.starlark.elements.BinaryOperator.PLUS
import org.morfly.airin.starlark.lang.*
import org.morfly.airin.starlark.lang.api.LanguageFeature


/**
 * Feature that enables concatenations that follow a `=` operator with backticks.
 *
 * Example:
 * ```
 * function1 {
 *     arg = "value1" `+` "value2" `+` "value3"
 *         ^           ^            ^
 *        (=)      (first +)    (second +)
 * }
 * ```
 * In the first example an argument is being passed using regular = operator. The order of operations is the following:
 * - first +
 * - second +
 * - =
 *
 * ```
 * function2 {
 *     arg `=` "value1" `+` "value2" `+` "value3"
 *          ^            ^            ^
 *         (=)       (first +)    (second +)
 * }
 * ```
 * However, in the second example a dynamic `=` operator is being used (with backticks). Since it's a regular Kotlin
 * function, the order of operations is the following:
 * - =
 * - first +
 * - second +
 *
 * This feature makes sure the entire concatenation is registered as an argument.
 */
internal interface DynamicBinaryPlusFeature : LanguageFeature {

    /**
     * String concatenation operator.
     */
    infix fun _StringValueAccumulator.`+`(other: StringType?): _StringValueAccumulator {
        holder.value = StringBinaryOperation(
            left = holder.value,
            operator = PLUS,
            right = Expression(other, ::StringLiteral)
        )
        return this
    }

    /**
     * String concatenation operator.
     */
    infix fun _NumberValueAccumulator.`+`(other: NumberType?): _NumberValueAccumulator {
        holder.value = NumberBinaryOperation(
            left = holder.value,
            operator = PLUS,
            right = Expression(other, ::NumberLiteral)
        )
        return this
    }

    /**
     * List concatenation operator.
     */
    infix fun <T> _ListValueAccumulator<T>.`+`(other: List<T>?): _ListValueAccumulator<T> {
        holder.value = ListBinaryOperation<T>(
            left = holder.value,
            operator = PLUS,
            right = Expression(other, ::ListExpression)
        )
        return this
    }

    /**
     * Tuple concatenation operator.
     */
    infix fun _TupleValueAccumulator.`+`(other: TupleType?): _TupleValueAccumulator {
        holder.value = TupleBinaryOperation(
            left = holder.value,
            operator = PLUS,
            right = Expression(other, ::TupleExpression)
        )
        return this
    }

    /**
     * Dictionary concatenation operator.
     */
    infix fun <K, V : Value> _DictionaryValueAccumulator<K, V>.`+`(other: Map<*, Value>?): _DictionaryValueAccumulator<K, V> {
        holder.value = DictionaryBinaryOperation<Key, Value>(
            left = holder.value,
            operator = PLUS,
            right = Expression(other, ::DictionaryExpression)
        )
        return this
    }

    /**
     * Dictionary concatenation operator.
     */
    infix fun <K, V : Value> _DictionaryValueAccumulator<K, V>.`+`(body: DictionaryContext.() -> Unit): _DictionaryValueAccumulator<K, V> {
        holder.value = DictionaryBinaryOperation<Key, Value>(
            left = holder.value,
            operator = PLUS,
            right = DictionaryExpression(DictionaryContext().apply(body).kwargs)
        )
        return this
    }

    /**
     * Concatenation operator for values for with type does not matter.
     */
    infix fun _AnyValueAccumulator.`+`(other: Any?): _AnyValueAccumulator {
        holder.value = AnyBinaryOperation(
            left = holder.value,
            operator = PLUS,
            right = Expression(other)
        )
        return this
    }
}