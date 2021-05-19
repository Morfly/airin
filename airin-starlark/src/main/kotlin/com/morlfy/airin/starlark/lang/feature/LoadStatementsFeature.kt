package com.morlfy.airin.starlark.lang.feature

import com.morlfy.airin.starlark.elements.LoadStatement
import com.morlfy.airin.starlark.elements.StringLiteral
import com.morlfy.airin.starlark.lang.StringType


internal interface LoadStatementsFeature : LanguageFeature, StatementsHolder {

    fun load(file: String, vararg symbols: String) {
        val symbols = symbols.map {
            LoadStatement.Symbol(
                name = StringLiteral(it),
                alias = null
            )
        }
        statements += LoadStatement(file = StringLiteral(file), symbols)
    }

    fun load(file: String, body: LoadContext.() -> Unit) {
        val symbols = LoadContext().apply(body).symbols
        statements += LoadStatement(
            file = StringLiteral(file),
            symbols = symbols
        )
    }

    fun load(file: String, vararg symbols: StringType)

    infix fun String.`=`(name: StringType): Pairr {
        return Pairr(this, name.toString())
    }

    class Pairr(val first: String, val second: String) : StringType by ""
}

class LoadContext : LanguageFeature {

    val symbols = mutableListOf<LoadStatement.Symbol>()

    infix fun String.`=`(name: StringType) {
        symbols += LoadStatement.Symbol(
            name = StringLiteral(name),
            alias = this
        )
    }
}

internal fun LoadStatementsFeature.test() {
    load("file") { "alias" `=` "value" }

    load("file", "alias" `=` "rule", "rule2")
}

