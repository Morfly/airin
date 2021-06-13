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

package org.morfly.airin.starlark.lang.feature

import org.morfly.airin.starlark.elements.*
import org.morfly.airin.starlark.lang.*
import org.morfly.airin.starlark.lang.api.LanguageFeature
import org.morfly.airin.starlark.lang.api.StatementsHolder


/**
 * Feature that enables assigning new values to the existing variable.
 */
internal interface ReassignmentsFeature : LanguageFeature, StatementsHolder {

    /**
     * String assignment operator.
     */
    infix fun StringReference.`=`(value: StringType?): _StringValueAccumulator {
        val assignment = Assignment(name, value = Expression(value, ::StringLiteral))
        statements += assignment
        return _StringValueAccumulator(assignment)
    }

    /**
     * Number assignment operator.
     */
    infix fun NumberReference.`=`(value: NumberType?): _NumberValueAccumulator {
        val assignment = Assignment(name, value = Expression(value, ::NumberLiteral))
        statements += assignment
        return _NumberValueAccumulator(assignment)
    }

    /**
     * List assignment operator.
     */
    infix fun <T> ListReference<T>.`=`(value: List<T>?): _ListValueAccumulator<T> {
        val assignment = Assignment(name, value = Expression(value, ::ListExpression))
        statements += assignment
        return _ListValueAccumulator(assignment)
    }

    /**
     * List assignment operator.
     */
    infix fun TupleReference.`=`(value: TupleType?): _TupleValueAccumulator {
        val assignment = Assignment(name, value = Expression(value, ::TupleExpression))
        statements += assignment
        return _TupleValueAccumulator(assignment)
    }

    /**
     * Dictionary assignment operator.
     */
    infix fun <K : Key, V : Value> DictionaryReference<K, V>.`=`(value: Map<Key, Value>?): _DictionaryValueAccumulator<K, V> {
        val assignment = Assignment(name, value = Expression(value, ::DictionaryExpression))
        statements += assignment
        return _DictionaryValueAccumulator(assignment)
    }

    /**
     * Dictionary assignment operator.
     */
    infix fun <K : Key, V : Value> DictionaryReference<K, V>.`=`(body: DictionaryContext.() -> Unit): _DictionaryValueAccumulator<K, V> {
        val value = DictionaryContext().apply(body).kwargs
        val assignment = Assignment(name, value = DictionaryExpression(value))
        statements += assignment
        return _DictionaryValueAccumulator(assignment)
    }
}