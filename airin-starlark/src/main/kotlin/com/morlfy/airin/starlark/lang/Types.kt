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

package com.morlfy.airin.starlark.lang


/**
 *
 */
typealias StringType = CharSequence

/**
 *
 */
typealias IntegerType = Comparable<Long>

/**
 *
 */
typealias FloatType = Comparable<Double>

/**
 *
 */
typealias BooleanType = Comparable<Boolean>

/**
 *
 */
typealias Key = Any?

/**
 *
 */
typealias Value = Any?

/**
 *
 */
typealias Name = StringType

/**
 *
 */
typealias Label = StringType


/**
 *
 */
sealed interface UnspecifiedValue

/**
 *
 */
object UnspecifiedString : UnspecifiedValue,
    StringType by ""

/**
 *
 */
object UnspecifiedInteger : UnspecifiedValue,
    IntegerType by 0L

/**
 *
 */
object UnspecifiedBoolean : UnspecifiedValue,
    BooleanType by false

/**
 *
 */
object UnspecifiedList : UnspecifiedValue,
    List<Nothing> by emptyList()

/**
 *
 */
object UnspecifiedDictionary : UnspecifiedValue,
    Map<Key, Value> by emptyMap()

/**
 *
 */
object UnspecifiedAnyValue : UnspecifiedValue