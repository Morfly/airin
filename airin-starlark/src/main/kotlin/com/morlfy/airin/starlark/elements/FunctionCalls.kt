package com.morlfy.airin.starlark.elements

import com.morlfy.airin.starlark.lang.StringType
import com.morlfy.airin.starlark.lang.Value


/**
 * Abstract element for function calls.
 *
 * @param name name of the function.
 * @param args arguments of the function
 */
sealed class FunctionCall(
    val name: String,
    val args: Set<Argument>
) : Statement, Expression {

    override fun <A> accept(visitor: ElementVisitor<A>, indentIndex: Int, mode: PositionMode, accumulator: A) {
        visitor.visit(this, indentIndex, mode, accumulator)
    }
}

/**
 * Element for a call of the function that returns string.
 */
class StringFunctionCall(
    name: String,
    args: Set<Argument>
) : FunctionCall(name, args),
    StringType by name

/**
 * Call of the function that returns [List].
 */
class ListFunctionCall<T>(
    name: String,
    args: Set<Argument>
) : FunctionCall(name, args),
    List<T> by emptyList()

/**
 * Call of the function that returns Dictionary. TODO
 */
class DictionaryFunctionCall<K /*: Key*/, V : Value>(
    name: String,
    args: Set<Argument>
) : FunctionCall(name, args),
    Map<K, V> by emptyMap()

/**
 * Call of the function that returns any other type (including void).
 */
class AnyFunctionCall(
    name: String,
    args: Set<Argument>
) : FunctionCall(name, args)

/**
 *
 */
typealias VoidFunctionCall = AnyFunctionCall

