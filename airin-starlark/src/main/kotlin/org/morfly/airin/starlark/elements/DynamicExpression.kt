package org.morfly.airin.starlark.elements


/**
 * Syntax element for a value that can be modified by the next elements during the syntax tree composition via DSL.
 *
 * @param value the expression that van be modified by the next elements in a syntax tree.
 */
class DynamicExpression(
    override var value: Expression
) : Expression, ExpressionHolder<Expression> {

    override val host: Expression
        get() = this

    override fun <A> accept(visitor: ElementVisitor<A>, position: Int, mode: PositionMode, accumulator: A) {
        visitor.visit(this, position, mode, accumulator)
    }
}