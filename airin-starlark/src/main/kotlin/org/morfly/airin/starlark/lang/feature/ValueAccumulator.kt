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

import org.morfly.airin.starlark.elements.ValueHolder
import org.morfly.airin.starlark.lang.Key
import org.morfly.airin.starlark.lang.Value


/**
 * Abstract accumulator of values for dynamic operations.
 */
interface _ValueAccumulator {

    val holder: ValueHolder
}

/**
 * Accumulator of string values for dynamic operations.
 */
@JvmInline
value class _StringValueAccumulator(override val holder: ValueHolder) : _ValueAccumulator

/**
 * Accumulator of number values for dynamic operations.
 */
@JvmInline
value class _NumberValueAccumulator(override val holder: ValueHolder) : _ValueAccumulator

/**
 * Accumulator of boolean values for dynamic operations.
 */
@JvmInline
value class _BooleanValueAccumulator(override val holder: ValueHolder) : _ValueAccumulator

/**
 * Accumulator of list values for dynamic operations.
 */
@JvmInline
value class _ListValueAccumulator<T>(override val holder: ValueHolder) : _ValueAccumulator

/**
 * Accumulator of tuple values for dynamic operations.
 */
@JvmInline
value class _TupleValueAccumulator(override val holder: ValueHolder) : _ValueAccumulator

/**
 * Accumulator of dictionary values for dynamic operations.
 */
@JvmInline
value class _DictionaryValueAccumulator<K : Key, V : Value>(override val holder: ValueHolder) : _ValueAccumulator

/**
 * Accumulator of any other values for dynamic operations.
 */
@JvmInline
value class _AnyValueAccumulator(override val holder: ValueHolder) : _ValueAccumulator