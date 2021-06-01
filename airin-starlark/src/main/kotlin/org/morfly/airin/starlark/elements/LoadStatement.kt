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


/**
 * A syntax element for a load statement.
 *
 * @param file the file to be loaded.
 * @param symbols the list of symbols to be loaded to the environment.
 */
class LoadStatement(
    val file: StringLiteral,
    val symbols: List<Symbol>
) : Statement {

    /**
     * A syntax element for a symbol of a load statement.
     *
     * A load statement symbol represents rules, functions and constants that are to be loaded to the environment.
     */
    class Symbol(
        val name: StringLiteral,
        val alias: String?
    ) : Element {

        override fun <A> accept(visitor: ElementVisitor<A>, position: Int, mode: PositionMode, accumulator: A) {
            visitor.visit(this, position, mode, accumulator)
        }
    }

    override fun <A> accept(visitor: ElementVisitor<A>, position: Int, mode: PositionMode, accumulator: A) {
        visitor.visit(this, position, mode, accumulator)
    }
}