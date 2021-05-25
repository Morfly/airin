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

@file:Suppress("FunctionName")

package com.morlfy.airin.starlark.lang.feature

import com.morlfy.airin.starlark.elements.*
import com.morlfy.airin.starlark.elements.BinaryOperator.PLUS
import com.morlfy.airin.starlark.lang.Key
import com.morlfy.airin.starlark.lang.StringType
import com.morlfy.airin.starlark.lang.Value


/**
 *
 */
internal interface BinaryPlusFeature : LanguageFeature {

    /**
     *
     */
    infix fun StringType?.`+`(other: StringType?): StringType =
        StringBinaryOperation(
            left = Expression(this),
            operator = PLUS,
            right = other?.let(::StringLiteral)
        )

    /**
     *
     */
    infix fun <T> List<T>?.`+`(other: List<T>?): List<T> =
        ListBinaryOperation(
            left = Expression(this),
            operator = PLUS,
            right = other?.let(::ListExpression)
        )

    /**
     *
     */
    infix fun Map<*, Value>?.`+`(other: Map<*, Value>?): Map<Key, Value> =
        DictionaryBinaryOperation(
            left = Expression(this),
            operator = PLUS,
            right = other?.let(::DictionaryExpression)
        )

    /**
     *
     */
    infix fun Map<*, Value>?.`+`(body: DictionaryContext.() -> Unit): Map<Key, Value> =
        DictionaryBinaryOperation(
            left = Expression(this),
            operator = PLUS,
            right = DictionaryExpression(DictionaryContext().apply(body).kwargs)
        )
}