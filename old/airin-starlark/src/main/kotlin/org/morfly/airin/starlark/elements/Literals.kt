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

package org.morfly.airin.starlark.elements

import org.morfly.airin.starlark.lang.*


/**
 * An abstract element for any Starlark literal.
 */
sealed interface Literal : Expression

/**
 * An element for a string literal.
 */
@JvmInline
value class StringLiteral(val value: StringType) : Literal {

    override fun <A> accept(visitor: ElementVisitor<A>, position: Int, mode: PositionMode, accumulator: A) {
        visitor.visit(this, position, mode, accumulator)
    }
}

/**
 * An element for an integer literal.
 */
@JvmInline // TODO
value class IntegerLiteral(val value: Long) : Literal {

    override fun <A> accept(visitor: ElementVisitor<A>, position: Int, mode: PositionMode, accumulator: A) {
        visitor.visit(this, position, mode, accumulator)
    }
}

/**
 * An element for a float literal.
 */
@JvmInline // TODO
value class FloatLiteral(val value: Double) : Literal {

    override fun <A> accept(visitor: ElementVisitor<A>, position: Int, mode: PositionMode, accumulator: A) {
        visitor.visit(this, position, mode, accumulator)
    }
}

/**
 *
 */
fun NumberLiteral(value: NumberType): Literal =
    when (value) {
        is Long, is Int, is Short, is Byte -> IntegerLiteral(value.toLong())
        is Double, is Float -> FloatLiteral(value.toDouble())
        else -> StringLiteral(value.toString())
    }

/**
 * An element for a boolean literal.
 */
@JvmInline
value class BooleanLiteral(val value: BooleanType) : Literal {

    override fun <A> accept(visitor: ElementVisitor<A>, position: Int, mode: PositionMode, accumulator: A) {
        visitor.visit(this, position, mode, accumulator)
    }
}