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

@file:Suppress("FunctionName")

package com.morlfy.airin.starlark.elements


/**
 *
 */
class Argument(
    val id: String,
    override var value: Expression?
) : ValueHolder {

    // comparison by id only
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Argument
        if (id != other.id) return false
        return true
    }

    override fun hashCode() = id.hashCode()


    override fun <A> accept(visitor: ElementVisitor<A>, position: Int, mode: PositionMode, accumulator: A) {
        visitor.visit(this, position, mode, accumulator)
    }
}

/**
 *
 */
fun Arguments(args: Map<String, *>): LinkedHashSet<Argument> =
    args.mapTo(linkedSetOf()) { (id, value) -> Argument(id, Expression(value)) }