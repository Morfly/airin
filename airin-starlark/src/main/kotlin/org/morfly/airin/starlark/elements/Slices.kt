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

import org.morfly.airin.starlark.lang.StringType


/**
 *
 */
sealed class Slice(
    val expression: Expression,
    val start: Int?,
    val end: Int?,
    val step: Int?
) : Expression {

    override fun <A> accept(visitor: ElementVisitor<A>, position: Int, mode: PositionMode, accumulator: A) {
        visitor.visit(this, position, mode, accumulator)
    }
}

/**
 *
 */
class StringSlice(
    expression: Expression,
    start: Int?,
    end: Int?,
    step: Int?
) : Slice(expression, start, end, step),
    StringType by ""

/**
 *
 */
class ListSlice<T>(
    expression: Expression,
    start: Int?,
    end: Int?,
    step: Int?
) : Slice(expression, start, end, step),
    List<T> by emptyList()