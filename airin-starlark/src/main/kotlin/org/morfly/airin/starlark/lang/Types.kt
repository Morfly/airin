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

package org.morfly.airin.starlark.lang


/**
 * Representation of a string type from Starlark.
 */
typealias StringType = CharSequence

/**
 * Representation of an integer type from Starlark.
 */
typealias IntegerType = Comparable<Long>

/**
 * Representation of a float type from Starlark.
 */
typealias FloatType = Comparable<Double>

/**
 * Representation of an boolean type from Starlark.
 */
typealias BooleanType = Comparable<Boolean>

/**
 * Representation of an tuple type from Starlark.
 */
typealias TupleType<T> = List<T>

/**
 * Type alias for a dictionary key type.
 */
typealias Key = Any?

/**
 * Type alias for a dictionary value type.
 */
typealias Value = Any?

/**
 * String type alias for the value representing name in Bazel.
 */
typealias Name = StringType

/**
 * String type alias for the value representing Bazel label.
 */
typealias Label = StringType


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
 * Unspecified value of a string type.
 */
object UnspecifiedString : UnspecifiedValue,
    StringType by ""

/**
 * Unspecified value of an integer type.
 */
object UnspecifiedInteger : UnspecifiedValue,
    IntegerType by 0L

/**
 * Unspecified value of boolean type.
 */
object UnspecifiedBoolean : UnspecifiedValue,
    BooleanType by false

/**
 * Unspecified value of a list type.
 */
object UnspecifiedList : UnspecifiedValue,
    List<Nothing> by emptyList()

/**
 * Unspecified value of a dictionary type.
 */
object UnspecifiedDictionary : UnspecifiedValue,
    Map<Key, Value> by emptyMap()

/**
 * Unspecified value for cases where type does not matter.
 */
object UnspecifiedAny : UnspecifiedValue