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

package org.morfly.airin.starlark.elements

import org.morfly.airin.starlark.lang.StringType
import org.morfly.airin.starlark.lang.Value


/**
 * Syntax element for binary operation.
 */
sealed class BinaryOperation(
    val left: Expression?,
    val operator: BinaryOperator,
    val right: Expression?
) : Expression {

    override fun <A> accept(visitor: ElementVisitor<A>, position: Int, mode: PositionMode, accumulator: A) {
        visitor.visit(this, position, mode, accumulator)
    }
}

/**
 * Syntax element for binary operation of strings.
 *
 * Conforms to the dictionary type.
 */
class StringBinaryOperation(
    left: Expression?,
    operator: BinaryOperator,
    right: Expression?
) : BinaryOperation(left, operator, right),
    StringType by ""

/**
 * Syntax element for binary operation of lists.
 *
 * Conforms to the list type.
 */
class ListBinaryOperation<T>(
    left: Expression?,
    operator: BinaryOperator,
    right: Expression?
) : BinaryOperation(left, operator, right),
    List<T> by emptyList()

/**
 * Syntax element for binary operation of dictionaries.
 *
 * Conforms to the dictionary type.
 */
class DictionaryBinaryOperation<K /*: Key*/, V : Value>(
    left: Expression?,
    operator: BinaryOperator,
    right: Expression?
) : BinaryOperation(left, operator, right),
    Map<K, V> by emptyMap()

/**
 * Syntax element for binary operation of objects of any type.
 */
class AnyBinaryOperation(
    left: Expression?,
    operator: BinaryOperator,
    right: Expression?
) : BinaryOperation(left, operator, right)