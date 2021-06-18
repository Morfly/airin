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

@file:Suppress("ClassName", "unused")

package org.morfly.airin.starlark.lang.feature

import org.morfly.airin.starlark.elements.Element
import org.morfly.airin.starlark.elements.ExpressionHolder
import org.morfly.airin.starlark.lang.Key
import org.morfly.airin.starlark.lang.Value


/**
 * Abstract accumulator of expressions for dynamic operations.
 */
interface _ExpressionAccumulator<H : Element> {

    val holder: ExpressionHolder<H>
}

/**
 * Accumulator of string expressions for dynamic operations.
 */
@JvmInline
value class _StringExpressionAccumulator<H : Element>(
    override val holder: ExpressionHolder<H>
) : _ExpressionAccumulator<H>

/**
 * Accumulator of number expressions for dynamic operations.
 */
@JvmInline
value class _NumberExpressionAccumulator<H : Element>(
    override val holder: ExpressionHolder<H>
) : _ExpressionAccumulator<H>

/**
 * Accumulator of boolean expressions for dynamic operations.
 */
@JvmInline
value class _BooleanExpressionAccumulator<H : Element>(
    override val holder: ExpressionHolder<H>
) : _ExpressionAccumulator<H>

/**
 * Accumulator of list expressions for dynamic operations.
 */
@JvmInline
value class _ListExpressionAccumulator<T, H : Element>(
    override val holder: ExpressionHolder<H>
) : _ExpressionAccumulator<H>

/**
 * Accumulator of tuple expressions for dynamic operations.
 */
@JvmInline
value class _TupleExpressionAccumulator<H : Element>(
    override val holder: ExpressionHolder<H>
) : _ExpressionAccumulator<H>

/**
 * Accumulator of dictionary expressions for dynamic operations.
 */
@JvmInline
value class _DictionaryExpressionAccumulator<K : Key, V : Value, H : Element>(
    override val holder: ExpressionHolder<H>
) : _ExpressionAccumulator<H>

/**
 * Accumulator of any other expressions for dynamic operations.
 */
@JvmInline
value class _AnyExpressionAccumulator<H : Element>(
    override val holder: ExpressionHolder<H>
) : _ExpressionAccumulator<H>