package com.morlfy.airin.starlark.elements

import com.morlfy.airin.starlark.lang.StringType


/**
 *
 */
sealed interface Literal : Expression

/**
 *
 */
@JvmInline
value class StringLiteral(val value: StringType) : Literal {

    override fun <A> accept(visitor: ElementVisitor<A>, position: Int, mode: PositionMode, accumulator: A) {
        visitor.visit(this, position, mode, accumulator)
    }
}

/**
 *
 */
@JvmInline
value class IntegerLiteral(val value: Long) : Literal {

    override fun <A> accept(visitor: ElementVisitor<A>, position: Int, mode: PositionMode, accumulator: A) {
        visitor.visit(this, position, mode, accumulator)
    }
}

/**
 *
 */
@JvmInline
value class FloatLiteral(val value: Double) : Literal {

    override fun <A> accept(visitor: ElementVisitor<A>, position: Int, mode: PositionMode, accumulator: A) {
        visitor.visit(this, position, mode, accumulator)
    }
}

/**
 *
 */
@JvmInline
value class BooleanLiteral(val value: Boolean) : Literal {

    override fun <A> accept(visitor: ElementVisitor<A>, position: Int, mode: PositionMode, accumulator: A) {
        visitor.visit(this, position, mode, accumulator)
    }
}