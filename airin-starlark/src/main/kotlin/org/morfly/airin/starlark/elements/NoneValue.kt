package org.morfly.airin.starlark.elements


/**
 *
 */
object NoneValue: Expression {

    override fun <A> accept(visitor: ElementVisitor<A>, position: Int, mode: PositionMode, accumulator: A) {
        visitor.visit(this, position, mode, accumulator)
    }
}