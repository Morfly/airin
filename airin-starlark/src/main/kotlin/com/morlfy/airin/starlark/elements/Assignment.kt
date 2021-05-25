package com.morlfy.airin.starlark.elements


/**
 *
 */
class Assignment(
    val name: String,
    override var value: Expression?
) : ValueHolder, Statement {

    override fun <A> accept(visitor: ElementVisitor<A>, position: Int, mode: PositionMode, accumulator: A) {
        visitor.visit(this, position, mode, accumulator)
    }
}