package com.morlfy.airin.starlark.lang.feature

import com.morlfy.airin.starlark.elements.EmptyLineStatement


/**
 *
 */
internal interface EmptyLinesFeature : LanguageFeature, StatementsHolder {

    val space: Unit
        get() {
            statements += EmptyLineStatement
        }
}