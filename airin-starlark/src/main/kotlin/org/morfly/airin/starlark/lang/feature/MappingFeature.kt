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
import org.morfly.airin.starlark.lang.*
import org.morfly.airin.starlark.lang.api.LanguageFeature


/**
 * Container of the key value pairs that are part of the dictionary expression.
 */
internal interface MappingHolder {

    /**
     *
     */
    val kwargs: MutableMap<Expression, Expression>
}

/**
 * Enables mapping to key-value pairs that are part of the dictionary expression.
 */
internal interface MappingFeature : LanguageFeature, MappingHolder {

    /**
     * Mapping key to string value.
     */
    infix fun Key.to(value: StringType): _StringExpressionAccumulator<Expression> {
        val k = Expression(this)
        val v = DynamicExpression(Expression(value, ::StringLiteral))
        kwargs[k] = v
        return _StringExpressionAccumulator(v)
    }

    /**
     * Mapping key to number value.
     */
    infix fun Key.to(value: NumberType): _NumberExpressionAccumulator<Expression> {
        val k = Expression(this)
        val v = DynamicExpression(Expression(value, ::NumberLiteral))
        kwargs[k] = v
        return _NumberExpressionAccumulator(v)
    }

    /**
     * Mapping key to list value.
     */
    infix fun <T> Key.to(value: List<T>): _ListExpressionAccumulator<T, Expression> {
        val k = Expression(this)
        val v = DynamicExpression(Expression(value, ::ListExpression))
        kwargs[k] = v
        return _ListExpressionAccumulator(v)
    }

    /**
     * Mapping key to tuple value.
     */
    infix fun Key.to(value: TupleType): _TupleExpressionAccumulator<Expression> {
        val k = Expression(this)
        val v = DynamicExpression(Expression(value, ::TupleExpression))
        kwargs[k] = v
        return _TupleExpressionAccumulator(v)
    }

    /**
     * Mapping key to dictionary value.
     */
    infix fun <K : Key, V : Value> Key.to(value: Map<K, V>): _DictionaryExpressionAccumulator<K, V, Expression> {
        val k = Expression(this)
        val v = DynamicExpression(Expression(value, ::DictionaryExpression))
        kwargs[k] = v
        return _DictionaryExpressionAccumulator(v)
    }

    /**
     * Mapping key to dictionary value.
     */
    infix fun Key.to(body: DictionaryContext.() -> Unit): _DictionaryExpressionAccumulator<Key, Value, Expression> {
        val value = DictionaryContext().apply(body).kwargs
        val k = Expression(this)
        val v = DynamicExpression(Expression(value, ::DictionaryExpression))
        kwargs[k] = v
        return _DictionaryExpressionAccumulator(v)
    }

    /**
     * Mapping key to value of any type.
     */
    infix fun Key.to(value: Any?): _AnyExpressionAccumulator<Expression> {
        val k = Expression(this)
        val v = DynamicExpression(Expression(value))
        kwargs[k] = v
        return _AnyExpressionAccumulator(v)
    }
}