package com.morlfy.airin.starlark.lang.feature

import com.morlfy.airin.starlark.elements.LoadStatement
import com.morlfy.airin.starlark.elements.StringLiteral


/**
 *
 */
internal interface LoadStatementsFeature : LanguageFeature, StatementsHolder {

    /**
     *
     */
    fun load(file: String, vararg symbols: String) {
        val elements = symbols.map {
            LoadStatement.Symbol(
                name = StringLiteral(it),
                alias = null
            )
        }
        statements += LoadStatement(file = StringLiteral(file), symbols = elements)
    }
}

