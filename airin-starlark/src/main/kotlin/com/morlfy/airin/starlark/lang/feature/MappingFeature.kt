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

@file:Suppress("SpellCheckingInspection")

package com.morlfy.airin.starlark.lang.feature

import com.morlfy.airin.starlark.elements.*
import com.morlfy.airin.starlark.lang.Key
import com.morlfy.airin.starlark.lang.StringType
import com.morlfy.airin.starlark.lang.Value


/**
 *
 */
interface MappingHolder {

    /**
     *
     */
    val kwargs: MutableMap<Expression?, Expression?>
}

/**
 *
 */
internal interface MappingFeature : LanguageFeature, MappingHolder {

    /**
     *
     */
    infix fun Key.to(value: StringType): _ValueAccumulator<StringType> =
        registerKeyValuePair(key = this, value = Expression(value, ::StringLiteral))

    /**
     *
     */
    infix fun <T> Key.to(value: List<T>): _ValueAccumulator<List<T>> =
        registerKeyValuePair(key = this, value = Expression(value, ::ListExpression))

    /**
     *
     */
    infix fun <K : Key, V : Value> Key.to(value: Map<K, V>): _ValueAccumulator<Map<K, V>> =
        registerKeyValuePair(key = this, value = Expression(value, ::DictionaryExpression))

    /**
     *
     */
    infix fun Key.to(body: DictionaryContext.() -> Unit): _ValueAccumulator<Map<Key, Value>> =
        registerKeyValuePair(key = this, value = DictionaryExpression(DictionaryContext().apply(body).kwargs))

    /**
     *
     */
    infix fun Key.to(value: Any?): _ValueAccumulator<Any> =
        registerKeyValuePair(key = this, value = Expression(value))
}

/**
 *
 */
internal fun <T> MappingFeature.registerKeyValuePair(key: Key, value: Expression?): _ValueAccumulator<T> {
    val k = Expression(key)
    val v = DynamicValue(value)
    kwargs[k] = v
    return _ValueAccumulator(v)
}