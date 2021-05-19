package com.morlfy.airin.starlark.lang.feature

import com.morlfy.airin.starlark.elements.Statement


interface StatementsHolder {

    val statements: MutableList<Statement>
}