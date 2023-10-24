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

import org.morfly.airin.starlark.lang.*


/**
 * Syntax element for binary operation.
 */
sealed interface BinaryOperation : Expression {
    val left: Expression
    val operator: BinaryOperator
    val right: Expression

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
    override val left: Expression,
    override val operator: BinaryOperator,
    override val right: Expression
) : BinaryOperation,
    StringType by StringTypeDelegate()

/**
 * Syntax element for binary operation of integers.
 *
 * Conforms to the integer type.
 */
class NumberBinaryOperation(
    override val left: Expression,
    override val operator: BinaryOperator,
    override val right: Expression
) : BinaryOperation,
    NumberTypeDelegate()

/**
 * Syntax element for binary operation of booleans.
 *
 * Conforms to the boolean type.
 */
class BooleanBinaryOperation(
    override val left: Expression,
    override val operator: BinaryOperator,
    override val right: Expression
) : BinaryOperation,
    BooleanType by BooleanTypeDelegate()

/**
 * Syntax elemStringBinaryOperationent for binary operation of lists.
 *
 * Conforms to the list type.
 */
class ListBinaryOperation<T>(
    override val left: Expression,
    override val operator: BinaryOperator,
    override val right: Expression
) : BinaryOperation,
    List<T> by ListTypeDelegate()

/**
 * Syntax elemStringBinaryOperationent for binary operation of lists.
 *
 * Conforms to the list type.
 */
class TupleBinaryOperation(
    override val left: Expression,
    override val operator: BinaryOperator,
    override val right: Expression
) : BinaryOperation,
    TupleType by TupleTypeDelegate()

/**
 * Syntax element for binary operation of dictionaries.
 *
 * Conforms to the dictionary type.
 */
class DictionaryBinaryOperation<K /*: Key*/, V : Value>(
    override val left: Expression,
    override val operator: BinaryOperator,
    override val right: Expression
) : BinaryOperation,
    Map<K, V> by DictionaryTypeDelegate()

/**
 * Syntax element for binary operation of objects of any type.
 */
class AnyBinaryOperation(
    override val left: Expression,
    override val operator: BinaryOperator,
    override val right: Expression
) : BinaryOperation