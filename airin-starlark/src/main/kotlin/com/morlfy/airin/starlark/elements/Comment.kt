package com.morlfy.airin.starlark.elements


/**
 *
 */
@JvmInline
value class Comment(val value: String) : Statement {

    override fun <A> accept(visitor: ElementVisitor<A>, indentIndex: Int, mode: PositionMode, accumulator: A) {
        visitor.visit(this, indentIndex, mode, accumulator)
    }
}