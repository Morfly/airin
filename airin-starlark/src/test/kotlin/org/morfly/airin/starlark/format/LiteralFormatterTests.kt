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

package org.morfly.airin.starlark.format

import org.morfly.airin.starlark.elements.BooleanLiteral
import org.morfly.airin.starlark.elements.FloatLiteral
import org.morfly.airin.starlark.elements.IntegerLiteral
import org.morfly.airin.starlark.elements.PositionMode.CONTINUE_LINE
import org.morfly.airin.starlark.elements.PositionMode.NEW_LINE
import org.morfly.airin.starlark.elements.StringLiteral
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe


class LiteralFormatterTests : ShouldSpec({
    val formatter = StarlarkCodeFormatter(indentSize = 4)
    val ___4 = " ".repeat(4) // 1st position indentation

    context("string literal formatter") {

        context("NEW LINE mode") {

            should("format single line string") {
                val literal = StringLiteral("string")

                val builder = StringBuilder()
                formatter.visit(literal, position = 1, NEW_LINE, builder)

                val expectedResult = """
                    |${___4}"string"
                """.trimMargin()

                builder.toString() shouldBe expectedResult
            }

            should("format multiline string") {
                val string = """
                    line1
                    line2
                """.trimIndent()
                val literal = StringLiteral(string)

                val builder = StringBuilder()
                formatter.visit(literal, position = 1, NEW_LINE, builder)

                val qqq = "\"\"\""
                val expectedResult = """
                    |${___4}$qqq
                    |${___4}line1
                    |${___4}line2
                    |${___4}$qqq
                """.trimMargin()

                builder.toString() shouldBe expectedResult
            }

            should("format multiline string with initial indents using") {
                val string = """
                    |  line1
                    | line2
                    |line3
                """.trimMargin()

                val literal = StringLiteral(string)

                val builder = StringBuilder()
                formatter.visit(literal, position = 1, NEW_LINE, builder)

                val qqq = "\"\"\""
                val expectedResult = """
                    |${___4}$qqq
                    |${___4}  line1
                    |${___4} line2
                    |${___4}line3
                    |${___4}$qqq
                """.trimMargin()

                builder.toString() shouldBe expectedResult
            }
        }

        context("CONTINUE LINE mode") {

            should("format single line string") {
                val literal = StringLiteral("string")

                val builder = StringBuilder()
                formatter.visit(literal, position = 1, CONTINUE_LINE, builder)

                val expectedResult = """
                    |"string"
                """.trimMargin()

                builder.toString() shouldBe expectedResult
            }

            should("format multiline string") {
                val string = """
                    line1
                    line2
                """.trimIndent()
                val literal = StringLiteral(string)

                val builder = StringBuilder()
                formatter.visit(literal, position = 1, CONTINUE_LINE, builder)

                val qqq = "\"\"\""
                val expectedResult = """
                    |$qqq
                    |${___4}line1
                    |${___4}line2
                    |${___4}$qqq
                """.trimMargin()

                builder.toString() shouldBe expectedResult
            }

            should("format multiline string with initial indents using") {
                val string = """
                    |  line1
                    | line2
                    |line3
                """.trimMargin()

                val literal = StringLiteral(string)

                val builder = StringBuilder()
                formatter.visit(literal, position = 1, CONTINUE_LINE, builder)

                val qqq = "\"\"\""
                val expectedResult = """
                    |$qqq
                    |${___4}  line1
                    |${___4} line2
                    |${___4}line3
                    |${___4}$qqq
                """.trimMargin()

                builder.toString() shouldBe expectedResult
            }
        }
    }

    context("number literal formatter") {

        context("NEW LINE mode") {

            should("format integer number") {
                val literal = IntegerLiteral(123)

                val builder = StringBuilder()
                formatter.visit(literal, position = 1, NEW_LINE, builder)

                val expectedResult = "${___4}123"

                builder.toString() shouldBe expectedResult
            }

            should("format float number") {
                val literal = FloatLiteral(12.3)

                val builder = StringBuilder()
                formatter.visit(literal, position = 1, NEW_LINE, builder)

                val expectedResult = "${___4}12.3"

                builder.toString() shouldBe expectedResult
            }
        }

        context("CONTINUE LINE mode") {

            should("format integer number") {
                val literal = IntegerLiteral(123)

                val builder = StringBuilder()
                formatter.visit(literal, position = 1, CONTINUE_LINE, builder)

                val expectedResult = "123"

                builder.toString() shouldBe expectedResult
            }

            should("format float number") {
                val literal = FloatLiteral(12.3)

                val builder = StringBuilder()
                formatter.visit(literal, position = 1, CONTINUE_LINE, builder)

                val expectedResult = "12.3"

                builder.toString() shouldBe expectedResult
            }
        }
    }

    context("boolean literal formatter") {

        context("NEW LINE mode") {

            should("format true value") {
                val literal = BooleanLiteral(true)

                val builder = StringBuilder()
                formatter.visit(literal, position = 1, NEW_LINE, builder)

                val expectedResult = "${___4}True"

                builder.toString() shouldBe expectedResult
            }

            should("format false value") {
                val literal = BooleanLiteral(false)

                val builder = StringBuilder()
                formatter.visit(literal, position = 1, NEW_LINE, builder)

                val expectedResult = "${___4}False"

                builder.toString() shouldBe expectedResult
            }
        }

        context("CONTINUE LINE mode") {

            should("format true value") {
                val literal = BooleanLiteral(true)

                val builder = StringBuilder()
                formatter.visit(literal, position = 1, CONTINUE_LINE, builder)

                val expectedResult = "True"

                builder.toString() shouldBe expectedResult
            }

            should("format false value") {
                val literal = BooleanLiteral(false)

                val builder = StringBuilder()
                formatter.visit(literal, position = 1, CONTINUE_LINE, builder)

                val expectedResult = "False"

                builder.toString() shouldBe expectedResult
            }
        }
    }
})