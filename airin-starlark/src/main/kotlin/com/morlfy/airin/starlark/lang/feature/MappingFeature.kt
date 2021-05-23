@file:Suppress("SpellCheckingInspection")

package com.morlfy.airin.starlark.lang.feature

import com.morlfy.airin.starlark.elements.DictionaryExpression
import com.morlfy.airin.starlark.elements.DynamicValue
import com.morlfy.airin.starlark.elements.Expression
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
        registerKeyValuePair(key = this, value)

    /**
     *
     */
    infix fun <T> Key.to(value: List<T>): _ValueAccumulator<List<T>> =
        registerKeyValuePair(key = this, value)

    /**
     *
     */
    infix fun <K : Key, V : Value> Key.to(value: Map<K, V>): _ValueAccumulator<Map<K, V>> =
        registerKeyValuePair(key = this, value)

    /**
     *
     */
    infix fun Key.to(body: DictionaryContext.() -> Unit): _ValueAccumulator<Map<Key, Value>> =
        registerKeyValuePair(key = this, value = DictionaryExpression(DictionaryContext().apply(body).kwargs))

    /**
     *
     */
    infix fun Key.to(value: Any?): _ValueAccumulator<Any> =
        registerKeyValuePair(key = this, value)
}

/**
 *
 */
internal fun <T> MappingFeature.registerKeyValuePair(key: Key, value: Value): _ValueAccumulator<T> {
    val k = Expression(key)
    val v = DynamicValue(Expression(value))
    kwargs[k] = v
    return _ValueAccumulator(v)
}