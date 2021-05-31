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

package org.morfly.airin.starlark.lang.feature

import org.morfly.airin.starlark.elements.*
import org.morfly.airin.starlark.lang.Key
import org.morfly.airin.starlark.lang.StringType
import org.morfly.airin.starlark.lang.Value
import org.morfly.airin.starlark.lang.api.LanguageFeature


/**
 *
 */
internal interface MappingHolder {

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
    infix fun Key.to(value: StringType): _StringValueAccumulator {
        val k = Expression(this)
        val v = DynamicValue(Expression(value, ::StringLiteral))
        kwargs[k] = v
        return _StringValueAccumulator(v)
    }

    /**
     *
     */
    infix fun <T> Key.to(value: List<T>): _ListValueAccumulator<T> {
        val k = Expression(this)
        val v = DynamicValue(Expression(value, ::ListExpression))
        kwargs[k] = v
        return _ListValueAccumulator(v)
    }

    /**
     *
     */
    infix fun <K : Key, V : Value> Key.to(value: Map<K, V>): _DictionaryValueAccumulator<K, V> {
        val k = Expression(this)
        val v = DynamicValue(Expression(value, ::DictionaryExpression))
        kwargs[k] = v
        return _DictionaryValueAccumulator(v)
    }

    /**
     *
     */
    infix fun Key.to(body: DictionaryContext.() -> Unit): _DictionaryValueAccumulator<Key, Value> {
        val value = DictionaryContext().apply(body).kwargs
        val k = Expression(this)
        val v = DynamicValue(Expression(value, ::DictionaryExpression))
        kwargs[k] = v
        return _DictionaryValueAccumulator(v)
    }

    /**
     *
     */
    infix fun Key.to(value: Any?): _AnyValueAccumulator {
        val k = Expression(this)
        val v = DynamicValue(Expression(value))
        kwargs[k] = v
        return _AnyValueAccumulator(v)
    }
}