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

package com.morlfy.airin.starlark.elements


/**
 *
 */
@JvmInline
value class ListExpression(val value: List<Expression?>) : Expression {

    override fun <A> accept(visitor: ElementVisitor<A>, position: Int, mode: PositionMode, accumulator: A) {
        visitor.visit(this, position, mode, accumulator)
    }
}

/**
 *
 */
fun <T> ListExpression(list: List<T>): ListExpression =
    ListExpression(list.map(::Expression))

/**
 *
 */
@JvmInline
value class DictionaryExpression(val value: Map<Expression?, Expression?>) : Expression {

    override fun <A> accept(visitor: ElementVisitor<A>, position: Int, mode: PositionMode, accumulator: A) {
        visitor.visit(this, position, mode, accumulator)
    }
}

/**
 *
 */
fun DictionaryExpression(dictionary: Map<*, *>): DictionaryExpression =
    DictionaryExpression(value = dictionary
        .mapKeys { (key, _) -> Expression(key) }
        .mapValues { (_, v) -> Expression(v) }
    )