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

package com.morlfy.airin.starlark.lang.feature

import com.morlfy.airin.starlark.elements.*
import com.morlfy.airin.starlark.elements.BinaryOperator.PLUS
import com.morlfy.airin.starlark.lang.Key
import com.morlfy.airin.starlark.lang.StringType
import com.morlfy.airin.starlark.lang.Value
import com.morlfy.airin.starlark.lang.api.LanguageFeature


/**
 *
 */
internal interface DynamicBinaryPlusFeature : LanguageFeature {

    /**
     *
     */
    infix fun _ValueAccumulator<StringType>.`+`(other: StringType?): _ValueAccumulator<StringType> {
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
    infix fun <T> _ValueAccumulator<List<T>>.`+`(other: List<T>?): _ValueAccumulator<List<T>> {
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
    infix fun <K, V : Value> _ValueAccumulator<Map<K, V>>.`+`(other: Map<*, Value>?): _ValueAccumulator<Map<K, V>> {
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
    infix fun <K, V : Value> _ValueAccumulator<Map<K, V>>.`+`(body: DictionaryContext.() -> Unit): _ValueAccumulator<Map<K, V>> {
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
    infix fun _ValueAccumulator<Any>.`+`(other: Any?): _ValueAccumulator<Any> {
        holder.value = AnyBinaryOperation(
            left = holder.value,
            operator = PLUS,
            right = Expression(other)
        )
        return this
    }
}