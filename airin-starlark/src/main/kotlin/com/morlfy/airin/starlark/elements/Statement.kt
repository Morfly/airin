package com.morlfy.airin.starlark.elements


/**
 *
 */
sealed interface Statement : Element

/**
 *
 */
object EmptyLineStatement : Statement {

    override fun <A> accept(visitor: ElementVisitor<A>, indentIndex: Int, mode: PositionMode, accumulator: A) {
        visitor.visit(this, indentIndex, mode, accumulator)
    }
}