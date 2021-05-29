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

@file:Suppress("ClassName")

package com.morlfy.airin.starlark.lang.feature

import com.morlfy.airin.starlark.elements.ValueHolder
import com.morlfy.airin.starlark.lang.Key
import com.morlfy.airin.starlark.lang.Value


//@Suppress("unused")
//open class _ValueAccumulator<T>(internal val holder: ValueHolder)
//
//class _StringValueAccumulator(holder: ValueHolder) : _ValueAccumulator<StringType>(holder)

interface _ValueAccumulator {
    val holder: ValueHolder
}

@JvmInline
value class _StringValueAccumulator(override val holder: ValueHolder) : _ValueAccumulator

@JvmInline
value class _ListValueAccumulator<T>(override val holder: ValueHolder) : _ValueAccumulator

@JvmInline
value class _DictionaryValueAccumulator<K : Key, V : Value>(override val holder: ValueHolder) : _ValueAccumulator

@JvmInline
value class _AnyValueAccumulator(override val holder: ValueHolder) : _ValueAccumulator