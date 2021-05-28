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

@file:Suppress("ClassName", "SpellCheckingInspection")

package com.morlfy.airin.starlark.lang.feature

import com.morlfy.airin.starlark.elements.DictionaryExpression
import com.morlfy.airin.starlark.elements.ListExpression
import com.morlfy.airin.starlark.elements.TupleExpression
import com.morlfy.airin.starlark.lang.Key
import com.morlfy.airin.starlark.lang.Value
import com.morlfy.airin.starlark.lang.api.LanguageFeature


/**
 *
 */
internal interface CollectionsFeature : LanguageFeature {

    // ===== Lists =====

    /**
     *
     */
    object _ListExpressionBuilder

    /**
     *
     */
    val list get() = _ListExpressionBuilder

    /**
     *
     */
    operator fun <T> _ListExpressionBuilder.get(vararg args: T): List<T> =
        ListExpression(listOf(*args))

    /**
     *
     */
    fun <T> list(vararg args: T): List<T> =
        ListExpression(listOf(*args))

    /**
     *
     */
    fun list(): List<Nothing> =
        ListExpression(emptyList())


    // ===== Dictionaries =====

    /**
     *
     */
    fun dict(body: DictionaryContext.() -> Unit): Map<Key, Value> {
        val kwargs = DictionaryContext().apply(body).kwargs
        return DictionaryExpression(kwargs)
    }

    /**
     * TODO remove
     */
    fun <K : Key, V : Value> dict(vararg kwargs: Pair<K, V>): Map<K, V> =
        kwargs.toMap()

    // ===== Tuples =====

    /**
     *
     */
    fun <T> tuple(vararg args: T): List<T> =
        TupleExpression(listOf(*args))

    /**
     *
     */
    fun tuple(): List<Nothing> =
        TupleExpression(emptyList())
}