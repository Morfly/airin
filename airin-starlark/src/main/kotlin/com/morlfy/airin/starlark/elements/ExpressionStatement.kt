package com.morlfy.airin.starlark.elements


/**
 *
 */
@JvmInline
value class ExpressionStatement(val expression: Expression?) : Statement {

    override fun <A> accept(visitor: ElementVisitor<A>, indentIndex: Int, mode: PositionMode, accumulator: A) {
        visitor.visit(this, indentIndex, mode, accumulator)
    }
}