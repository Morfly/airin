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

package org.morfly.airin.starlark.elements

import org.morfly.airin.starlark.lang.Value


/**
 * A raw text element is an injected string to the Starlark file template that is treated as is, without any formatting
 * or processing.
 *
 * The template engine does not validate the correctness of the injected code.
 */
sealed interface RawText : Element {

    /**
     * The raw text value that is injected to the file template.
     */
    val value: String

    override fun <A> accept(visitor: ElementVisitor<A>, position: Int, mode: PositionMode, accumulator: A) {
        visitor.visit(this, position, mode, accumulator)
    }
}

/**
 * A raw statement is a raw text element that iss treated like a statement.
 */
@JvmInline
value class RawStatement(override val value: String) : RawText, Statement

/**
 * A raw text element that is treated as an expression of string type.
 *
 * It allows to inject specific components of string type in larger expressions or statements.
 */
class StringRawExpression(override val value: String) : RawText, Expression,
    CharSequence by value

/**
 * A raw text element that is treated as an expression of list type.
 *
 * It allows to inject specific components of list type in larger expressions or statements.
 */
class ListRawExpression<T>(override val value: String) : RawText, Expression,
    List<T> by emptyList()

/**
 * A raw text element that is treated as an expression of dictionary type.
 *
 * It allows to inject specific components of dictionary type in larger expressions or statements.
 */
class DictionaryRawExpression<K, V : Value>(override val value: String) : RawText, Expression,
    Map<K, V> by emptyMap()

/**
 * A raw text element that is treated as an expression.
 *
 * It allows to inject specific components in larger expressions or statements.
 */
@JvmInline
value class AnyRawExpression(override val value: String) : RawText, Expression