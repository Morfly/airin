package org.morfly.airin.starlark.lang.api


/**
 * Language statement holder
 */
interface StatementsHolder<S> {

    /**
     * Language statements.
     */
    val statements: MutableList<S>
}