package com.morlfy.airin.starlark.lang.feature

import com.morlfy.airin.starlark.elements.Statement


/**
 *
 */
interface StatementsHolder {

    /**
     *
     */
    val statements: MutableList<Statement>
}

/**
 *
 */
@JvmInline
value class Statements(internal val value: MutableList<Statement>) {

    operator fun plusAssign(statement: Statement) {
        value += statement
    }
}