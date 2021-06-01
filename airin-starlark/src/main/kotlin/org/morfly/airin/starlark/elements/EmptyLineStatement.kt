package org.morfly.airin.starlark.elements


/**
 * Syntax node for a blank line in the file.
 */
object EmptyLineStatement : Statement {

    override fun <A> accept(visitor: ElementVisitor<A>, position: Int, mode: PositionMode, accumulator: A) {
        visitor.visit(this, position, mode, accumulator)
    }
}