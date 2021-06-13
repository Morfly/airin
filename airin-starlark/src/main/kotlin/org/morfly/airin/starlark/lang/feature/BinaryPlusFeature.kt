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

package org.morfly.airin.starlark.lang.feature

import org.morfly.airin.starlark.elements.*
import org.morfly.airin.starlark.elements.BinaryOperator.PLUS
import org.morfly.airin.starlark.lang.*
import org.morfly.airin.starlark.lang.api.LanguageFeature


/**
 * Feature that enables concatenations.
 */
internal interface BinaryPlusFeature : LanguageFeature {

    /**
     * Operator for string concatenation.
     */
    infix fun StringType?.`+`(other: StringType?): StringType =
        StringBinaryOperation(
            left = Expression(this, ::StringLiteral),
            operator = PLUS,
            right = Expression(other, ::StringLiteral)
        )

    /**
     * Operator for number concatenation.
     */
    infix fun NumberType?.`+`(other: NumberType?): NumberType =
        NumberBinaryOperation(
            left = Expression(this, ::NumberLiteral),
            operator = PLUS,
            right = Expression(other, ::NumberLiteral)
        )

    /**
     * Operator for boolean concatenation.
     */
    infix fun BooleanType?.`+`(other: BooleanType?): BooleanType =
        BooleanBinaryOperation(
            left = Expression(this, ::BooleanLiteral),
            operator = PLUS,
            right = Expression(other, ::BooleanLiteral)
        )

    /**
     * Operator for list or tuple concatenation.
     */
    infix fun <T> List<T>?.`+`(other: List<T>?): List<T> =
        ListBinaryOperation(
            left = Expression(this, ::ListExpression),
            operator = PLUS,
            right = Expression(other, ::ListExpression)
        )

    /**
     * Operator for dictionary concatenation.
     */
    infix fun Map<*, Value>?.`+`(other: Map<*, Value>?): Map<Key, Value> =
        DictionaryBinaryOperation(
            left = Expression(this, ::DictionaryExpression),
            operator = PLUS,
            right = Expression(other, ::DictionaryExpression)
        )

    /**
     * Operator for dictionary concatenation.
     */
    infix fun Map<*, Value>?.`+`(body: DictionaryContext.() -> Unit): Map<Key, Value> =
        DictionaryBinaryOperation(
            left = Expression(this, ::DictionaryExpression),
            operator = PLUS,
            right = DictionaryExpression(DictionaryContext().apply(body).kwargs)
        )
}