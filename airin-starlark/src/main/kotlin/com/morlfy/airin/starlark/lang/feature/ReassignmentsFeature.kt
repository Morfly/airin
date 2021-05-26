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
        val assignment = Assignment(name, value = Expression(value, ::StringLiteral))
        statements += assignment
        return _ValueAccumulator(assignment)
    }

    /**
     *
     */
    infix fun <T> ListReference<T>.`=`(value: List<T>?): _ValueAccumulator<List<T>> {
        val assignment = Assignment(name, value = Expression(value, ::ListExpression))
        statements += assignment
        return _ValueAccumulator(assignment)
    }

    /**
     *
     */
    infix fun <K : Key, V : Value> DictionaryReference<K, V>.`=`(value: Map<Key, Value>?): _ValueAccumulator<Map<K, V>> {
        val assignment = Assignment(name, value = Expression(value, ::DictionaryExpression))
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