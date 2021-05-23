@file:Suppress("FunctionName")

package com.morlfy.airin.starlark.lang.feature

import com.morlfy.airin.starlark.elements.*
import com.morlfy.airin.starlark.lang.Key
import com.morlfy.airin.starlark.lang.StringType
import com.morlfy.airin.starlark.lang.Value


/**
 *
 */
internal interface ReassignmentsFeature : LanguageFeature, StatementsHolder {

    /**
     *
     */
    infix fun StringReference.`=`(value: StringType?): _ValueAccumulator<StringType> {
        val assignment = Assignment(name, value = value?.let(::StringLiteral))
        statements += assignment
        return _ValueAccumulator(assignment)
    }

    /**
     *
     */
    infix fun <T> ListReference<T>.`=`(value: List<T>?): _ValueAccumulator<List<T>> {
        val assignment = Assignment(name, value = value?.let(::ListExpression))
        statements += assignment
        return _ValueAccumulator(assignment)
    }

    /**
     *
     */
    infix fun <K : Key, V : Value> DictionaryReference<K, V>.`=`(value: Map<Key, Value>?): _ValueAccumulator<Map<K, V>> {
        val assignment = Assignment(name, value = value?.let(::DictionaryExpression))
        statements += assignment
        return _ValueAccumulator(assignment)
    }

    /**
     *
     */
    infix fun <K : Key, V : Value> DictionaryReference<K, V>.`=`(body: DictionaryContext.() -> Unit): _ValueAccumulator<Map<K, V>> {
        val value = DictionaryContext().apply(body).kwargs
        val assignment = Assignment(name, value = DictionaryExpression(value))
        statements += assignment
        return _ValueAccumulator(assignment)
    }
}