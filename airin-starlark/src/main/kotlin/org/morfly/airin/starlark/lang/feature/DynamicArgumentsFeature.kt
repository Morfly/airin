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

@file:Suppress("SpellCheckingInspection", "FunctionName", "unused")

package org.morfly.airin.starlark.lang.feature

import org.morfly.airin.starlark.elements.*
import org.morfly.airin.starlark.lang.*
import org.morfly.airin.starlark.lang.api.LanguageFeature


/**
 * Defines an entity that collects argument elements.
 */
internal interface ArgumentsHolder {

    /**
     *
     */
    val fargs: LinkedHashSet<Argument>
}

/**
 * Feature of the Starlark template engine that provides operators for passsing arguments that were not initially
 * specified in Airin.
 */
internal interface DynamicArgumentsFeature : LanguageFeature, ArgumentsHolder {

    /**
     * Operator for passing string argument.
     */
    infix fun String.`=`(value: StringType): _StringExpressionAccumulator<*> {
        val argument = Argument(id = this, value = Expression(value, ::StringLiteral))
        fargs += argument
        return _StringExpressionAccumulator(argument)
    }

    /**
     * Operator for passing integer argument.
     */
    infix fun String.`=`(value: NumberType): _NumberExpressionAccumulator<*> {
        val argument = Argument(id = this, value = Expression(value, ::NumberLiteral))
        fargs += argument
        return _NumberExpressionAccumulator(argument)
    }

    /**
     * Operator for passing float argument.
     */
    infix fun String.`=`(value: BooleanType): _BooleanExpressionAccumulator<*> {
        val argument = Argument(id = this, value = Expression(value, ::BooleanLiteral))
        fargs += argument
        return _BooleanExpressionAccumulator(argument)
    }

    /**
     * Operator for passing list argument.
     */
    infix fun <T> String.`=`(value: List<T>): _ListExpressionAccumulator<T, *> {
        val argument = Argument(id = this, value = Expression(value, ::ListExpression))
        fargs += argument
        return _ListExpressionAccumulator(argument)
    }

    /**
     * Operator for passing tuple argument.
     */
    infix fun String.`=`(value: TupleType): _TupleExpressionAccumulator<*> {
        val argument = Argument(id = this, value = Expression(value, ::TupleExpression))
        fargs += argument
        return _TupleExpressionAccumulator(argument)
    }

    /**
     * Operator for passing dictionary argument.
     */
    infix fun <K : Key, V : Value> String.`=`(value: Map<K, V>): _DictionaryExpressionAccumulator<K, V, *> {
        val argument = Argument(id = this, value = Expression(value, ::DictionaryExpression))
        fargs += argument
        return _DictionaryExpressionAccumulator(argument)
    }

    /**
     * Operator for passing dictionary argument.
     */
    infix fun String.`=`(body: DictionaryContext.() -> Unit): _DictionaryExpressionAccumulator<Key, Value, *> {
        val value = DictionaryContext().apply(body).kwargs
        val argument = Argument(id = this, value = DictionaryExpression(value))
        fargs += argument
        return _DictionaryExpressionAccumulator(argument)
    }

    /**
     * Operator for passing null or arguments of any other type.
     */
    infix fun String.`=`(value: Any?): _AnyExpressionAccumulator<*> {
        val argument = Argument(id = this, value = Expression(value))
        fargs += argument
        return _AnyExpressionAccumulator(argument)
    }
}