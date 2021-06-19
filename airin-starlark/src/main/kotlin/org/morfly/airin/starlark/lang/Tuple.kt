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
 *
 */
interface Tuple {

    /**
     *
     */
    val items: List<Any?>
}

/**
 *
 */
fun Tuple.toList(): List<Any?> = items

/**
 *
 */
fun List<Any?>.toTuple(): Tuple = TupleImpl(this)


@JvmInline
internal value class TupleImpl(override val items: List<Any?>) : Tuple

internal object EmptyTuple : Tuple {
    override val items: List<Any?> = emptyList()
}

internal fun tupleOf(vararg items: Any?): Tuple =
    TupleImpl(items.toList())

internal fun emptyTuple(): Tuple =
    EmptyTuple