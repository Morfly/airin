package com.morlfy.airin.starlark.lang.feature

import com.morlfy.airin.starlark.elements.Expression
import com.morlfy.airin.starlark.elements.RawStatement
import com.morlfy.airin.starlark.elements.StringLiteral


/**
 *
 */
internal interface CommentsFeature : LanguageFeature, StatementsHolder {

    /**
     *
     */
    val String.comment: Unit
        get() {
            statements += RawStatement(this)
        }

    fun comment(body: () -> String) =
        body().comment

    infix fun <T : Expression?> T.comment(body: () -> String): T {
//        body().comment
        return this
    }
}

internal fun CommentsFeature.main() {
    "sfsfa".comment
    """
        
    """.trimIndent().comment

    comment { "some comment" }

    StringLiteral("string") comment { "some comment" }
}