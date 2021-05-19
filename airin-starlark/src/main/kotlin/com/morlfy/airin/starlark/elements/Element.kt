package com.morlfy.airin.starlark.elements


/**
 *
 */
sealed interface Element {

    /**
     *
     */
    fun <A> accept(visitor: ElementVisitor<A>, indentIndex: Int, mode: PositionMode, accumulator: A)
}