/*
 * Copyright 2021 Pavlo Stavytskyi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.morlfy.airin.starlark.lang.feature

import com.morlfy.airin.starlark.elements.Expression
import com.morlfy.airin.starlark.elements.RawStatement
import com.morlfy.airin.starlark.elements.StringLiteral
import com.morlfy.airin.starlark.lang.api.LanguageFeature


/**
 *
 */
internal interface CommentsFeature : LanguageFeature, StarlarkStatementsHolder {

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