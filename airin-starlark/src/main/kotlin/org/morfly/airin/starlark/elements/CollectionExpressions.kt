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

package org.morfly.airin.starlark.elements

import org.morfly.airin.starlark.lang.*


/**
 * Syntax element for a list expression.
 */
class ListExpression<T>(val value: List<Expression>, items: List<T>) : Expression,
    List<T> by ListTypeDelegate(items) {

    override fun <A> accept(visitor: ElementVisitor<A>, position: Int, mode: PositionMode, accumulator: A) {
        visitor.visit(this, position, mode, accumulator)
    }
}

/**
 * Factory function for creating list expression object from the items of any type.
 */
fun <T> ListExpression(list: List<T>): ListExpression<T> =
    ListExpression(list.map(::Expression), list)

/**
 * Syntax element for a dictionary expression.
 */
class DictionaryExpression(val value: Map<Expression, Expression>) : Expression,
    Map<Key, Value> by DictionaryTypeDelegate() {

    override fun <A> accept(visitor: ElementVisitor<A>, position: Int, mode: PositionMode, accumulator: A) {
        visitor.visit(this, position, mode, accumulator)
    }
}

/**
 * Factory function for creating dictionary expression object from the keys and values of any type.
 */
fun DictionaryExpression(dictionary: Map<*, *>): DictionaryExpression =
    DictionaryExpression(value = dictionary
        .mapKeys { (key, _) -> Expression(key) }
        .mapValues { (_, v) -> Expression(v) }
    )

/**
 * Syntax element for a tuple expression.
 */
class TupleExpression(val value: List<Expression>) : Expression,
    TupleType by TupleTypeDelegate() {

    override fun <A> accept(visitor: ElementVisitor<A>, position: Int, mode: PositionMode, accumulator: A) {
        visitor.visit(this, position, mode, accumulator)
    }
}

/**
 * Factory function for creating tuple expression object from the items of any type.
 */
fun TupleExpression(tuple: Tuple): TupleExpression =
    TupleExpression(tuple.items.map(::Expression))