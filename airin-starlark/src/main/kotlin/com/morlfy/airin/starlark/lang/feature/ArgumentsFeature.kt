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

@file:Suppress("SpellCheckingInspection")

package com.morlfy.airin.starlark.lang.feature

import com.morlfy.airin.starlark.elements.*
import com.morlfy.airin.starlark.lang.Key
import com.morlfy.airin.starlark.lang.StringType
import com.morlfy.airin.starlark.lang.Value
import com.morlfy.airin.starlark.lang.api.LanguageFeature


/**
 *
 */
internal interface ArgumentsHolder {

    /**
     *
     */
    val fargs: LinkedHashSet<Argument>
}

/**
 *
 */
internal interface ArgumentsFeature : LanguageFeature, ArgumentsHolder {

    /**
     *
     */
    infix fun String.`=`(value: StringType): _StringValueAccumulator {
        val argument = Argument(id = this, value = Expression(value, ::StringLiteral))
        fargs += argument
        return _StringValueAccumulator(argument)
    }

    /**
     *
     */
    infix fun <T> String.`=`(value: List<T>): _ListValueAccumulator<T> {
        val argument = Argument(id = this, value = Expression(value, ::ListExpression))
        fargs += argument
        return _ListValueAccumulator(argument)
    }

    /**
     *
     */
    infix fun <K : Key, V : Value> String.`=`(value: Map<K, V>): _DictionaryValueAccumulator<K, V> {
        val argument = Argument(id = this, value = Expression(value, ::DictionaryExpression))
        fargs += argument
        return _DictionaryValueAccumulator(argument)
    }

    /**
     *
     */
    infix fun String.`=`(body: DictionaryContext.() -> Unit): _DictionaryValueAccumulator<Key, Value> {
        val value = DictionaryContext().apply(body).kwargs
        val argument = Argument(id = this, value = DictionaryExpression(value))
        fargs += argument
        return _DictionaryValueAccumulator(argument)
    }

    /**
     * handles null and any arg
     */
    infix fun String.`=`(value: Any?): _AnyValueAccumulator {
        val argument = Argument(id = this, value = Expression(value))
        fargs += argument
        return _AnyValueAccumulator(argument)
    }
}