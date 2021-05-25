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

package com.morlfy.airin.starlark.elements

import com.morlfy.airin.starlark.lang.StringType
import com.morlfy.airin.starlark.lang.Value


/**
 *
 */
sealed interface Reference : Expression {

    /**
     *
     */
    val name: String

    override fun <A> accept(visitor: ElementVisitor<A>, position: Int, mode: PositionMode, accumulator: A) {
        visitor.visit(this, position, mode, accumulator)
    }
}

/**
 *
 */
class StringReference(override val name: String) : Reference,
    StringType by name

/**
 *
 */
class ListReference<out T>(override val name: String) : Reference,
    List<T> by emptyList()

/**
 * TODO
 */
class DictionaryReference<K /*: Key*/, V : Value>(override val name: String) : Reference,
    Map<K, V> by emptyMap()

/**
 *
 */
@JvmInline
value class AnyReference(override val name: String) : Reference