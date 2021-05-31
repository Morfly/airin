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
import org.morfly.airin.starlark.lang.Key
import org.morfly.airin.starlark.lang.StringType
import org.morfly.airin.starlark.lang.Value
import org.morfly.airin.starlark.lang.api.LanguageFeature


/**
 *
 */
internal interface DynamicBinaryPlusFeature : LanguageFeature {

    /**
     *
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
     *
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
     *
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
     *
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
     *
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