package com.morlfy.airin.starlark.elements


/**
 *
 */
class LoadStatement(
    val file: StringLiteral,
    val symbols: List<Symbol>
) : Statement {

    /**
     *
     */
    class Symbol(
        val name: StringLiteral,
        val alias: String?
    ) : Element {

        override fun <A> accept(visitor: ElementVisitor<A>, indentIndex: Int, mode: PositionMode, accumulator: A) {
            visitor.visit(this, indentIndex, mode, accumulator)
        }
    }

    override fun <A> accept(visitor: ElementVisitor<A>, indentIndex: Int, mode: PositionMode, accumulator: A) {
        visitor.visit(this, indentIndex, mode, accumulator)
    }
}