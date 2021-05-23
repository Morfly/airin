@file:Suppress("FunctionName")

package com.morlfy.airin.starlark.lang.feature

import com.morlfy.airin.starlark.elements.*
import com.morlfy.airin.starlark.elements.BinaryOperator.PLUS
import com.morlfy.airin.starlark.lang.Key
import com.morlfy.airin.starlark.lang.StringType
import com.morlfy.airin.starlark.lang.Value


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
            right = other?.let(::StringLiteral)
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
            right = other?.let(::ListExpression)
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
            right = other?.let(::DictionaryExpression)
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