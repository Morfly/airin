@file:Suppress("LocalVariableName")

package com.morlfy.airin.starlark.lang.feature

import com.morlfy.airin.starlark.elements.DictionaryExpression
import com.morlfy.airin.starlark.elements.Expression
import com.morlfy.airin.starlark.elements.DynamicValue
import com.morlfy.airin.starlark.lang.Key
import com.morlfy.airin.starlark.lang.StringType
import com.morlfy.airin.starlark.lang.Value


/**
 *
 */
interface MappingHolder {

    val dictionary: MutableMap<Expression?, Expression?>
}

/**
 *
 */
internal interface MappingFeature : LanguageFeature, MappingHolder {

    infix fun Key.to(value: StringType?): ValueAccumulator<StringType> =
        registerKeyValuePair(key = this, value)

    infix fun Key.to(value: List<Value>?): ValueAccumulator<List<Value>> =
        registerKeyValuePair(key = this, value)

    infix fun Key.to(value: Map<Key, Value>): ValueAccumulator<Map<Key, Value>> =
        registerKeyValuePair(key = this, value)

    infix fun Key.to(body: DictionaryContext.() -> Unit): ValueAccumulator<Value> =
        registerKeyValuePair(key = this, value = DictionaryExpression(DictionaryContext().apply(body).dictionary))
}

internal fun <T> MappingFeature.registerKeyValuePair(key: Key, value: Value): ValueAccumulator<T> {
    val k = Expression(key)
    val v = DynamicValue(Expression(value))
    dictionary[k] = v
    return ValueAccumulator(v)
}

/**
 *
 */
class DictionaryContext : MappingFeature, DynamicBinaryPlusFeature, CollectionsFeature {

    override val dictionary = mutableMapOf<Expression?, Expression?>()
}