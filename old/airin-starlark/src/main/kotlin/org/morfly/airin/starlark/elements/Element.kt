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
 * An Element is a node in a Starlark syntax tree.
 *
 * Elements are constructed with the Airin's DSL functions.
 */
sealed interface Element {

    /**
     * Implements the double dispatch by redirecting the call to the node specific `visit` method of the [ElementVisitor].
     *
     * @param visitor the [ElementVisitor] instance to dispatch to.
     * @param position the horizontal position of the element that is usually represented by the indentation index.
     * @param mode a position mode in relation to the previous element in the syntax tree.
     * @param accumulator an accumulator of values being collected while traversing elements in a syntax tree.
     */
    fun <A> accept(visitor: ElementVisitor<A>, position: Int, mode: PositionMode, accumulator: A)
}