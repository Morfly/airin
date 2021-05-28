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

package com.morlfy.airin.starlark.lang.feature

import com.morlfy.airin.starlark.elements.*
import com.morlfy.airin.starlark.lang.*
import kotlin.reflect.KClass
import kotlin.reflect.typeOf

// FIXME
// ===== Function call statements =====

/**
 *
 */
fun StatementsHolder.registerFunctionCallStatement(name: String, args: Set<Argument> = emptySet()) {
    statements += ExpressionStatement(VoidFunctionCall(name, args))
}

/**
 *
 */
fun StatementsHolder.registerFunctionCallStatement(name: String, args: Map<String, *>) {
    registerFunctionCallStatement(name, Arguments(args))
}

/**
 *
 */
inline fun <T : FunctionCallContext> StatementsHolder.registerFunctionCallStatement(
    name: String, context: T, body: T.() -> Unit
) {
    val args = context.apply(body).fargs
    registerFunctionCallStatement(name, args)
}

// ===== Function call expressions =====

/**
 *
 */
fun stringFunctionCall(name: String, args: Set<Argument> = emptySet()): StringType =
    StringFunctionCall(name, args)

/**
 *
 */
fun stringFunctionCall(name: String, args: Map<String, *>): StringType =
    StringFunctionCall(name, args = Arguments(args))

/**
 *
 */
fun <T> listFunctionCall(name: String, args: Set<Argument> = emptySet()): List<T> =
    ListFunctionCall(name, args)

/**
 *
 */
fun <T> listFunctionCall(name: String, args: Map<String, *>): List<T> =
    ListFunctionCall(name, args = Arguments(args))

/**
 *
 */
fun dictFunctionCall(name: String, args: Set<Argument> = emptySet()): Map<Key, Value> =
    DictionaryFunctionCall(name, args)

/**
 *
 */
fun dictFunctionCall(name: String, args: Map<String, *>): Map<Key, Value> =
    DictionaryFunctionCall(name, args = Arguments(args))

/**
 *
 */
fun intFunctionCall(name: String, args: Set<Argument> = emptySet()): IntegerType =
    TODO("IntegerFunctionCall")

/**
 *
 */
fun intFunctionCall(name: String, args: Map<String, *>): IntegerType =
    intFunctionCall(name, args = Arguments(args))

/**
 *
 */
fun floatFunctionCall(name: String, args: Set<Argument> = emptySet()): FloatType =
    TODO("FloatFunctionCall")

/**
 *
 */
fun floatFunctionCall(name: String, args: Map<String, *>): FloatType =
    floatFunctionCall(name, args = Arguments(args))

/**
 *
 */
fun booleanFunctionCall(name: String, args: Set<Argument> = emptySet()): BooleanType =
    TODO("BooleanFunctionCall")

/**
 *
 */
fun booleanFunctionCall(name: String, args: Map<String, *>): BooleanType =
    booleanFunctionCall(name, args = Arguments(args))

/**
 * Note: if the function you're building has a specific type DO NOT use this builder.
 */
@OptIn(ExperimentalStdlibApi::class)
inline fun <reified T> functionCallExpression(name: String, args: Set<Argument> = emptySet()): T =
    when {
        StringType::class.java.isAssignableFrom(T::class.java) -> StringFunctionCall(name, args)
        List::class.java.isAssignableFrom(T::class.java) -> ListFunctionCall<Value>(name, args)
        Map::class.java.isAssignableFrom(T::class.java) -> DictionaryFunctionCall<Key, Value>(name, args)
        // If T is Comparable, use the type of its generic parameter as a return type of the function being called.
        // For example, Long class implements Comparable<Long>, Boolean class implements Comparable<Boolean>, etc.
        Comparable::class.java.isAssignableFrom(T::class.java) -> {
            val type = typeOf<T>().arguments.first().type
            when (type?.classifier as? KClass<*>) {
                // IntegerType
                Long::class, Int::class -> TODO("IntegerFunctionCall")
                // FloatType
                Double::class, Float::class -> TODO("FloatFunctionCall")
                // BooleanType
                Boolean::class -> TODO("BooleanFunctionCall")
                else -> AnyFunctionCall(name, args)
            }
        }
        else -> AnyFunctionCall(name, args)
    } as T

/**
 *
 */
inline fun <reified T> functionCallExpression(name: String, args: Map<String, *>): T =
    functionCallExpression(name, args = Arguments(args))