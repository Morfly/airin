package org.morfly.airin.starlark.lang.api


/**
 *
 */
interface StatementsHolder<S> {

    /**
     *
     */
    val statements: MutableList<S>
}