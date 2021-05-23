@file:Suppress("ClassName")

package com.morlfy.airin.starlark.lang.feature

import com.morlfy.airin.starlark.lang.Key
import com.morlfy.airin.starlark.lang.Value


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
        listOf(*args)

    /**
     *
     */
    fun <T> list(vararg args: T): List<T> =
        listOf(*args)

    /**
     *
     */
    fun list(): List<Nothing> =
        emptyList()


    // ===== Dictionaries =====

    /**
     * TODO
     */
    fun dict(body: DictionaryContext.() -> Unit): Map<Key, Value> =
        DictionaryContext().apply(body).kwargs as Map<Key, Value>

    /**
     *
     */
    fun <K: Key, V: Value>dict(vararg kwargs: Pair<K, V>): Map<K, V> =
        kwargs.toMap()
}