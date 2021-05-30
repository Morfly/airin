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

import com.morlfy.airin.starlark.elements.LoadStatement
import com.morlfy.airin.starlark.elements.Reference
import com.morlfy.airin.starlark.elements.StringLiteral
import com.morlfy.airin.starlark.elements.StringReference
import com.morlfy.airin.starlark.lang.BuildContext
import com.morlfy.airin.starlark.lang.StringType
import com.morlfy.airin.starlark.lang.api.LanguageFeature


/**
 *
 */
internal interface LoadStatementsFeature : LanguageFeature, StarlarkStatementsHolder {

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

