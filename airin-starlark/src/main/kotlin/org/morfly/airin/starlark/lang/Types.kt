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
 * Representation of a number type from Starlark including integer and float numbers.
 */
typealias NumberType = Number

/**
 * TODO add description
 */
typealias BooleanBaseType<T> = Comparable<T>

/**
 * Representation of an boolean type from Starlark.
 */
typealias BooleanType = BooleanBaseType<Boolean>

/**
 * Representation of an tuple type from Starlark.
 */
typealias TupleType = Tuple

// TODO
typealias ListType<T> = List<T>

// TODO
typealias DictionaryType<K, V> = Map<K, V>

/**
 *
 */
typealias VoidType = Unit

/**
 *
 */
typealias BaseKey = Any

/**
 * Type alias for a dictionary key type.
 */
typealias Key = BaseKey?

/**
 *
 */
typealias BaseValue = Any
/**
 * Type alias for a dictionary value type.
 */
typealias Value = BaseValue?

/**
 * String type alias for the value representing name in Bazel.
 */
typealias Name = StringType

/**
 * String type alias for the value representing Bazel label.
 */
typealias Label = StringType

/**
 * TODO Placeholder for cases where generic parameter must be ignored
 */
typealias v = Unit