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
 * An abstract element for a function call.
 */
sealed interface FunctionCall : Expression {
    val name: String
    val args: Set<Argument>
    val receiver: Expression?

    override fun <A> accept(visitor: ElementVisitor<A>, position: Int, mode: PositionMode, accumulator: A) {
        visitor.visit(this, position, mode, accumulator)
    }
}

/**
 * An element for a call of the function that returns string.
 */
class StringFunctionCall(
    override val name: String,
    override val args: Set<Argument>,
    override val receiver: Expression? = null
) : FunctionCall,
    StringType by StringTypeDelegate()

/**
 * An element for a call of the function that returns integer.
 */
class NumberFunctionCall(
    override val name: String,
    override val args: Set<Argument>,
    override val receiver: Expression? = null
) : FunctionCall,
    NumberTypeDelegate()

/**
 * An element for a call of the function that returns boolean.
 */
class BooleanFunctionCall(
    override val name: String,
    override val args: Set<Argument>,
    override val receiver: Expression? = null
) : FunctionCall,
    BooleanType by BooleanTypeDelegate()

/**
 * An element for a call of the function that returns list.
 */
class ListFunctionCall<T>(
    override val name: String,
    override val args: Set<Argument>,
    override val receiver: Expression? = null
) : FunctionCall,
    List<T> by ListTypeDelegate()

class TupleFunctionCall(
    override val name: String,
    override val args: Set<Argument>,
    override val receiver: Expression? = null
) : FunctionCall,
    TupleType by TupleTypeDelegate()

/**
 * An element for a call of the function that returns dictionary.
 */
class DictionaryFunctionCall<K /*: Key*/, V : Value>(
    override val name: String,
    override val args: Set<Argument>,
    override val receiver: Expression? = null
) : FunctionCall,
    Map<K, V> by DictionaryTypeDelegate()

/**
 * An element for a call of the function when the return type does not matter.
 */
class AnyFunctionCall(
    override val name: String,
    override val args: Set<Argument>,
    override val receiver: Expression? = null
) : FunctionCall

/**
 * An element for a call of the function with no return value (void).
 */
typealias VoidFunctionCall = AnyFunctionCall

