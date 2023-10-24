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

package org.morfly.airin.starlark.lang.feature

import org.morfly.airin.starlark.elements.*
import org.morfly.airin.starlark.elements.BinaryOperator.PERCENT
import org.morfly.airin.starlark.lang.Key
import org.morfly.airin.starlark.lang.NumberType
import org.morfly.airin.starlark.lang.StringType
import org.morfly.airin.starlark.lang.Value
import org.morfly.airin.starlark.lang.api.LanguageFeature


/**
 * Feature that enables percent binary operator mostly used for string interpolation in Starlark.
 */
internal interface BinaryPercentsFeature : LanguageFeature {

    /**
     *
     */
    infix fun StringType?.`%`(other: StringType?): StringType =
        StringBinaryOperation(
            left = Expression(this, ::StringLiteral),
            operator = PERCENT,
            right = Expression(other, ::StringLiteral)
        )

    /**
     *
     */
    infix fun NumberType?.`%`(other: NumberType): NumberType =
        NumberBinaryOperation(
            left = Expression(this, ::NumberLiteral),
            operator = PERCENT,
            right = Expression(other, ::NumberLiteral)
        )
}