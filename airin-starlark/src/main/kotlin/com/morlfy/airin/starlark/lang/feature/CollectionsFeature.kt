package com.morlfy.airin.starlark.lang.feature

import com.morlfy.airin.starlark.lang.Key
import com.morlfy.airin.starlark.lang.Value


/**
 *
 */
// TODO add tuples support
internal interface CollectionsFeature : LanguageFeature {

    object _list

    /**
     *
     */
    val list get() = _list

    /**
     *
     */
    operator fun <T> _list.get(vararg args: T): List<T> =
        listOf(*args)

    /**
     *
     */
    fun <T> list(vararg args: T): List<T> =
        listOf(*args)

    /**
     * TODO test
     */
    fun list(): List<Value> =
        emptyList()

    /**
     *
     */
    fun dict(body: DictionaryContext.() -> Unit): Map<Key, Value> =
        DictionaryContext().apply(body).dictionary as Map<Key, Value>

    /**
     *
     */
    fun dict(vararg kwargs: Pair<Key, Value>): Map<Key, Value> =
        kwargs.toMap()
}

/**
 *
 */
// TODO explore Key, Value types
@LanguageFeatureContext
//class DictionaryContext1 : CollectionsFeature, BinaryPlusFeature, DynamicBinaryPlusFeature {
//
//    private val _kwargs = linkedMapOf<Key, Value>()
//
//    val kwargs: Map<Key, Value> get() = _kwargs
//
//    /**
//     *
//     */
//    infix fun Key.to(value: Value): Pair<Key, Value> {
//        _kwargs[this] = value
//        return Pair(this, value)
//    }
//
//    infix fun Key.to(body: DictionaryContext.() -> Unit): Pair<Key, Value> =
//        this to DictionaryContext().apply(body).kwargs
//}

internal fun CollectionsFeature.test() {
    val map: Map<Key, Value> = dict {
        "string" to "string"
        "list" to list["1", "2"]
        "list" to list("1", "2")
        "dict" to dict { "" to ""; "" to "" }
        "dict" to { "" to ""; "" to "" }
//        "dict" to dict("" to "", "" to "")
    }
}