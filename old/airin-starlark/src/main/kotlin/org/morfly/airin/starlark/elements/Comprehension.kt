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

import org.morfly.airin.starlark.lang.DictionaryTypeDelegate
import org.morfly.airin.starlark.lang.ListTypeDelegate
import org.morfly.airin.starlark.lang.Value


/**
 * Abstract syntax element for comprehension.
 */
sealed class Comprehension(
    internal val body: Expression,
    internal val clauses: MutableList<Clause>
) : Expression {

    /**
     * Syntax element for comprehension components.
     */
    sealed interface Clause : Element

    /**
     * For clause in the comprehension.
     */
    class For(
        val variables: List<Reference>,
        val iterable: Expression
    ) : Clause {

        override fun <A> accept(visitor: ElementVisitor<A>, position: Int, mode: PositionMode, accumulator: A) {
            visitor.visit(this, position, mode, accumulator)
        }
    }

    /**
     * If clause in the comprehension.
     */
    class If(
        val condition: Expression
    ) : Clause {

        override fun <A> accept(visitor: ElementVisitor<A>, position: Int, mode: PositionMode, accumulator: A) {
            visitor.visit(this, position, mode, accumulator)
        }
    }
}

/**
 * Syntax element for a list comprehension.
 *
 * Conforms to a list type and can be used in any place where the list type is expected.
 */
open class ListComprehension<out T> internal constructor(
    body: Expression,
    clauses: MutableList<Clause>
) : Comprehension(body, clauses),
    List<T> by ListTypeDelegate() {

    override fun <A> accept(visitor: ElementVisitor<A>, position: Int, mode: PositionMode, accumulator: A) {
        visitor.visit(this, position, mode, accumulator)
    }
}

/**
 * Syntax element for a dictionary comprehension.
 *
 * Conforms to a dictionary type and can be used in any place where the dictionary type is expected.
 */
class DictionaryComprehension<K, V : Value>(
    body: Expression,
    clauses: MutableList<Clause>
) : Comprehension(body, clauses),
    Map<K, V> by DictionaryTypeDelegate() {

    override fun <A> accept(visitor: ElementVisitor<A>, position: Int, mode: PositionMode, accumulator: A) {
        visitor.visit(this, position, mode, accumulator)
    }
}
