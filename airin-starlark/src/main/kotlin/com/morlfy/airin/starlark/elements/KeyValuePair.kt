package com.morlfy.airin.starlark.elements


/**
 *
 */
class KeyValuePair(
    val key: Expression?,
    override var value: Expression?
) : ValueHolder {

    override fun <A> accept(visitor: ElementVisitor<A>, indentIndex: Int, mode: PositionMode, accumulator: A) {
        visitor.visit(this, indentIndex, mode, accumulator)
    }
}