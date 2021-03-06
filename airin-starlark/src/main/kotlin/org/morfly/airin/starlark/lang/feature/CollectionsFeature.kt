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

package org.morfly.airin.starlark.lang.feature

import org.morfly.airin.starlark.elements.DictionaryExpression
import org.morfly.airin.starlark.elements.ListExpression
import org.morfly.airin.starlark.elements.TupleExpression
import org.morfly.airin.starlark.lang.*
import org.morfly.airin.starlark.lang.api.LanguageFeature


/**
 * Feature that enables list, dictionary and tuple expressions.
 */
internal interface CollectionsFeature : LanguageFeature {

    // ===== Lists =====

    object _ListExpressionBuilder

    val list get() = _ListExpressionBuilder

    /**
     * List expreession builder.
     */
    operator fun <T> _ListExpressionBuilder.get(vararg args: T): List<T> =
        ListExpression(listOf(*args))

    /**
     * List expression builder.
     */
    fun <T> list(vararg args: T): List<T> =
        ListExpression(listOf(*args))

    /**
     * Empty list builder.
     */
    fun list(): List<Nothing> =
        ListExpression(emptyList())


    // ===== Dictionaries =====

    /**
     * Dictionary expression builder.
     */
    fun dict(body: DictionaryContext.() -> Unit): Map<Key, Value> {
        val kwargs = DictionaryContext().apply(body).kwargs
        return DictionaryExpression(kwargs)
    }

    @Deprecated("Replace with dict{} expression builder", replaceWith = ReplaceWith("dict {}"))
    fun <K : Key, V : Value> dict(vararg kwargs: Pair<K, V>): Map<K, V> =
        kwargs.toMap()


    // ===== Tuples =====

    /**
     * Tuple expression builder.
     */
    fun tuple(vararg args: Any?): TupleType =
        TupleExpression(tupleOf(*args))

    /**
     * Empty tuple expression builder.
     */
    fun tuple(): TupleType =
        TupleExpression(emptyTuple())
}