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
 *
 * @param name the name of the function.
 * @param args arguments of the function
 */
sealed class FunctionCall(
    val name: String,
    val args: Set<Argument>
) : Expression {

    override fun <A> accept(visitor: ElementVisitor<A>, position: Int, mode: PositionMode, accumulator: A) {
        visitor.visit(this, position, mode, accumulator)
    }
}

/**
 * An element for a call of the function that returns string.
 */
class StringFunctionCall(
    name: String,
    args: Set<Argument>
) : FunctionCall(name, args),
    StringType by name

/**
 * An element for a call of the function that returns integer.
 */
class IntegerFunctionCall(
    name: String,
    args: Set<Argument>
) : FunctionCall(name, args),
    IntegerType by 0L

/**
 * An element for a call of the function that returns float.
 */
class FloatFunctionCall(
    name: String,
    args: Set<Argument>
) : FunctionCall(name, args),
    FloatType by 0.0

/**
 * An element for a call of the function that returns boolean.
 */
class BooleanFunctionCall(
    name: String,
    args: Set<Argument>
) : FunctionCall(name, args),
    BooleanType by false

/**
 * An element for a call of the function that returns list.
 */
class ListFunctionCall<T>(
    name: String,
    args: Set<Argument>
) : FunctionCall(name, args),
    List<T> by emptyList()

/**
 * An element for a call of the function that returns dictionary.
 */
class DictionaryFunctionCall<K /*: Key*/, V : Value>(
    name: String,
    args: Set<Argument>
) : FunctionCall(name, args),
    Map<K, V> by emptyMap()

/**
 * An element for a call of the function when the return type does not matter.
 */
class AnyFunctionCall(
    name: String,
    args: Set<Argument>
) : FunctionCall(name, args)

/**
 * An element for a call of the function with no return value (void).
 */
typealias VoidFunctionCall = AnyFunctionCall

