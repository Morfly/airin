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

@file:Suppress("unused")

package org.morfly.airin.starlark.lang


/**
 * Abstract builtin type that represents a value that was not set by the user of a template engine.
 *
 * The primary use case for this type is a default value of Kotlin functions that represent Bazel rules. Starlark functions
 * can have many arguments and user is not supposed to specify them all. Therefore, in Kotlin, such arguments must
 * be set with default values, so that they won't be printed while generating Starlark files.
 * Null, however, can't be used as such default values as user can also pass it as an argument that is supposed to be a
 * None value from Starlark.
 * That is why, a separate type for unspecified values is used in all places where those values should not be printed.
 */
sealed interface UnspecifiedValue

/**
 * Unspecified value of a number type.
 */
object UnspecifiedString : UnspecifiedValue,
    StringType by StringTypeDelegate()

/**
 * Unspecified value of a number type.
 */
object UnspecifiedNumber : UnspecifiedValue,
    NumberTypeDelegate()

/**
 * Unspecified value of boolean type.
 */
object UnspecifiedBoolean : UnspecifiedValue,
    BooleanType by BooleanTypeDelegate()

/**
 * Unspecified value of a list type.
 */
object UnspecifiedList : UnspecifiedValue,
    List<Nothing> by ListTypeDelegate()

/**
 * Unspecified value of a tuple type.
 */
object UnspecifiedTuple : UnspecifiedValue,
    TupleType by TupleTypeDelegate()

/**
 * Unspecified value of a dictionary type.
 */
object UnspecifiedDictionary : UnspecifiedValue,
    Map<Key, Value> by DictionaryTypeDelegate()

/**
 * Unspecified value for cases where type does not matter.
 */
object UnspecifiedAny : UnspecifiedValue