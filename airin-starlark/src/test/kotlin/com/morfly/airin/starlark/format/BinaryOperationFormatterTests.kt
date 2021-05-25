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

@file:Suppress("LocalVariableName")

package com.morfly.airin.starlark.format

import com.morlfy.airin.starlark.elements.BinaryOperator.PLUS
import com.morlfy.airin.starlark.elements.ListBinaryOperation
import com.morlfy.airin.starlark.elements.ListExpression
import com.morlfy.airin.starlark.elements.PositionMode.CONTINUE_LINE
import com.morlfy.airin.starlark.elements.PositionMode.NEW_LINE
import com.morlfy.airin.starlark.elements.StringBinaryOperation
import com.morlfy.airin.starlark.elements.StringLiteral
import com.morlfy.airin.starlark.format.StarlarkCodeFormatter
import com.morlfy.airin.starlark.lang.StringType
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe


class BinaryOperationFormatterTests : ShouldSpec({
    val formatter = StarlarkCodeFormatter(indentSize = 4)
    val ___4 = " ".repeat(4) // 1st position indentation
    val _______8 = " ".repeat(8) // 2nd position indentation

    context("binary plus formatter") {

        context("NEW LINE mode") {

            should("format string concatenation") {
                val concat = StringBinaryOperation(
                    left = StringLiteral("string1"),
                    operator = PLUS,
                    right = StringLiteral("string2")
                )

                val builder = StringBuilder()
                formatter.visit(concat, position = 1, NEW_LINE, builder)

                val expectedResult = """
                    |${___4}"string1" + "string2"
                """.trimMargin()

                builder.toString() shouldBe expectedResult
            }

            should("format string concatenation with null") {
                val concat = StringBinaryOperation(
                    left = StringLiteral("string1"),
                    operator = PLUS,
                    right = null
                )

                val builder = StringBuilder()
                formatter.visit(concat, position = 1, NEW_LINE, builder)

                val expectedResult = """
                    |${___4}"string1" + None
                """.trimMargin()

                builder.toString() shouldBe expectedResult
            }

            should("format single line list concatenation") {
                val concat = ListBinaryOperation<StringType>(
                    left = ListExpression(listOf(StringLiteral("item1"))),
                    operator = PLUS,
                    right = ListExpression(listOf(StringLiteral("item2")))
                )

                val builder = StringBuilder()
                formatter.visit(concat, position = 1, NEW_LINE, builder)

                val expectedResult = """
                    |${___4}["item1"] + ["item2"]
                """.trimMargin()

                builder.toString() shouldBe expectedResult
            }

            should("format list concatenation") {
                val concat = ListBinaryOperation<StringType>(
                    left = ListExpression(listOf(StringLiteral("item1"), StringLiteral("item2"))),
                    operator = PLUS,
                    right = ListExpression(listOf(StringLiteral("item3"), StringLiteral("item4")))
                )

                val builder = StringBuilder()
                formatter.visit(concat, position = 1, NEW_LINE, builder)

                val expectedResult = """
                    |${___4}[
                    |${_______8}"item1",
                    |${_______8}"item2",
                    |${___4}] + [
                    |${_______8}"item3",
                    |${_______8}"item4",
                    |${___4}]
                """.trimMargin()

                builder.toString() shouldBe expectedResult
            }

            should("format multiple string concatenation") {
                val concat1 = StringBinaryOperation(
                    left = StringLiteral("string1"),
                    operator = PLUS,
                    right = StringLiteral("string2")
                )
                val concat2 = StringBinaryOperation(
                    left = concat1,
                    operator = PLUS,
                    right = StringLiteral("string3")
                )
                val concat3 = StringBinaryOperation(
                    left = concat2,
                    operator = PLUS,
                    right = StringLiteral("string4")
                )

                val builder = StringBuilder()
                formatter.visit(concat3, position = 1, NEW_LINE, builder)

                val expectedResult = """
                    |${___4}"string1" + "string2" + "string3" + "string4"
                """.trimMargin()

                builder.toString() shouldBe expectedResult
            }
        }

        context("CONTINUE LINE mode") {

            should("format string concatenation") {
                val concat = StringBinaryOperation(
                    left = StringLiteral("string1"),
                    operator = PLUS,
                    right = StringLiteral("string2")
                )

                val builder = StringBuilder()
                formatter.visit(concat, position = 1, CONTINUE_LINE, builder)

                val expectedResult = """
                    |"string1" + "string2"
                """.trimMargin()

                builder.toString() shouldBe expectedResult
            }

            should("format list concatenation") {
                val concat = ListBinaryOperation<StringType>(
                    left = ListExpression(listOf(StringLiteral("item1"), StringLiteral("item2"))),
                    operator = PLUS,
                    right = ListExpression(listOf(StringLiteral("item3"), StringLiteral("item4")))
                )

                val builder = StringBuilder()
                formatter.visit(concat, position = 1, CONTINUE_LINE, builder)

                val expectedResult = """
                    |[
                    |${_______8}"item1",
                    |${_______8}"item2",
                    |${___4}] + [
                    |${_______8}"item3",
                    |${_______8}"item4",
                    |${___4}]
                """.trimMargin()

                builder.toString() shouldBe expectedResult
            }
        }
    }
})