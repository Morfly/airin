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
    val fargs: LinkedHashMap<String, Argument>
}

fun LinkedHashMap<String, Argument>.asSet() =
    mapTo(linkedSetOf()) { it.value }

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
        fargs[this] = argument
        return _StringExpressionAccumulator(argument)
    }

    /**
     * Operator for passing integer argument.
     */
    infix fun String.`=`(value: NumberType): _NumberExpressionAccumulator<*> {
        val argument = Argument(id = this, value = Expression(value, ::NumberLiteral))
        fargs[this] = argument
        return _NumberExpressionAccumulator(argument)
    }

    /**
     * Operator for passing float argument.
     */
    infix fun String.`=`(value: BooleanType): _BooleanExpressionAccumulator<*> {
        val argument = Argument(id = this, value = Expression(value, ::BooleanLiteral))
        fargs[this] = argument
        return _BooleanExpressionAccumulator(argument)
    }

    /**
     * Operator for passing list argument.
     */
    infix fun <T> String.`=`(value: List<T>): _ListExpressionAccumulator<T, *> {
        val argument = append(
            name = this,
            value = Expression(value, ::ListExpression),
            concatenation = { left, op, right -> ListBinaryOperation<T>(left, op, right) }
        )
        return _ListExpressionAccumulator(argument)
    }

    /**
     * Operator for passing tuple argument.
     */
    infix fun String.`=`(value: TupleType): _TupleExpressionAccumulator<*> {
        val argument = Argument(id = this, value = Expression(value, ::TupleExpression))
        fargs[this] = argument
        return _TupleExpressionAccumulator(argument)
    }

    /**
     * Operator for passing dictionary argument.
     */
    infix fun <K : Key, V : Value> String.`=`(value: Map<K, V>): _DictionaryExpressionAccumulator<K, V, *> {
        val argument = append(
            name = this,
            value = Expression(value, ::DictionaryExpression),
            concatenation = { left, op, right -> DictionaryBinaryOperation<K, V>(left, op, right) }
        )
        return _DictionaryExpressionAccumulator(argument)
    }

    /**
     * Operator for passing dictionary argument.
     */
    infix fun String.`=`(body: DictionaryContext.() -> Unit): _DictionaryExpressionAccumulator<Key, Value, *> {
        val value = DictionaryContext().apply(body).kwargs
        val argument = append(
            name = this,
            value = DictionaryExpression(value),
            concatenation = { left, op, right -> DictionaryBinaryOperation<Key, Value>(left, op, right) }
        )
        return _DictionaryExpressionAccumulator(argument)
    }

    /**
     * Operator for passing null or arguments of any other type.
     */
    infix fun String.`=`(value: Any?): _AnyExpressionAccumulator<*> {
        val argument = Argument(id = this, value = Expression(value))
        fargs[this] = argument
        return _AnyExpressionAccumulator(argument)
    }
}

internal fun ArgumentsHolder.append(
    name: String,
    value: Expression,
    concatenation: (Expression, BinaryOperator, Expression) -> BinaryOperation
): Argument {
    val argument = if (name !in fargs) {
        Argument(id = name, value = value)
    } else {
        val leftExpression = fargs[name]!!.value
        Argument(
            id = name,
            value = concatenation(leftExpression, BinaryOperator.PLUS, value)
        )
    }
    fargs[name] = argument
    return argument
}