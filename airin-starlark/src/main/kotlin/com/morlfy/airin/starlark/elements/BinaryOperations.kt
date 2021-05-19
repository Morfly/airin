package com.morlfy.airin.starlark.elements

import com.morlfy.airin.starlark.lang.StringType
import com.morlfy.airin.starlark.lang.Value


/**
 *
 */
sealed class BinaryOperation(
    val left: Expression?,
    val operator: BinaryOperator,
    val right: Expression?
) : Expression {

    override fun <A> accept(visitor: ElementVisitor<A>, indentIndex: Int, mode: PositionMode, accumulator: A) {
        visitor.visit(this, indentIndex, mode, accumulator)
    }
}

/**
 *
 */
class StringBinaryOperation(
    left: Expression?,
    operator: BinaryOperator,
    right: Expression?
) : BinaryOperation(left, operator, right),
    StringType by ""

/**
 *
 */
class ListBinaryOperation<T>(
    left: Expression?,
    operator: BinaryOperator,
    right: Expression?
) : BinaryOperation(left, operator, right),
    List<T> by emptyList()

/**
 * TODO
 */
class DictionaryBinaryOperation<K /*: Key*/, V : Value>(
    left: Expression?,
    operator: BinaryOperator,
    right: Expression?
) : BinaryOperation(left, operator, right),
    Map<K, V> by emptyMap()

/**
 *
 */
class AnyBinaryOperation(
    left: Expression?,
    operator: BinaryOperator,
    right: Expression?
) : BinaryOperation(left, operator, right)