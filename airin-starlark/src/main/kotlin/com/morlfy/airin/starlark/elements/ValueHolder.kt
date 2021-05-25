package com.morlfy.airin.starlark.elements


/**
 *
 */
sealed interface ValueHolder : Element {

    var value: Expression?
}

/**
 *
 */
class DynamicValue(
    override var value: Expression?
) : Expression, ValueHolder {

    override fun <A> accept(visitor: ElementVisitor<A>, position: Int, mode: PositionMode, accumulator: A) {
        visitor.visit(this, position, mode, accumulator)
    }
}