package com.morlfy.airin.starlark.elements


/**
 *
 */
sealed interface Element {

    /**
     *
     */
    fun <A> accept(visitor: ElementVisitor<A>, position: Int, mode: PositionMode, accumulator: A)
}