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

@file:Suppress("FunctionName")

package org.morfly.airin.starlark.lang


abstract class NumberTypeDelegate internal constructor(
    private val value: NumberType = 0
) : NumberType() {
    override fun toByte() = value.toByte()
    override fun toChar() = value.toChar()
    override fun toDouble() = value.toDouble()
    override fun toFloat() = value.toFloat()
    override fun toInt() = value.toInt()
    override fun toLong() = value.toLong()
    override fun toShort() = value.toShort()
}

internal fun <T> ListTypeDelegate(value: List<T> = emptyList()): List<T> = value

internal fun TupleTypeDelegate(value: TupleType = emptyTuple()): TupleType = value

internal fun <K, V> DictionaryTypeDelegate(value: Map<K, V> = emptyMap()): Map<K, V> = value

internal fun StringTypeDelegate(value: StringType = ""): StringType = value

internal fun BooleanTypeDelegate(value: BooleanType = false): BooleanType = value