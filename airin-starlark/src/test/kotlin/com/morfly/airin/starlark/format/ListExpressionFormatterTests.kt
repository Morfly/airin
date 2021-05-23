@file:Suppress("LocalVariableName")

package com.morfly.airin.starlark.format

import com.morlfy.airin.starlark.elements.ListExpression
import com.morlfy.airin.starlark.elements.PositionMode.CONTINUE_LINE
import com.morlfy.airin.starlark.elements.PositionMode.NEW_LINE
import com.morlfy.airin.starlark.elements.StringLiteral
import com.morlfy.airin.starlark.format.ElementFormatter
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe


class ListExpressionFormatterTests : ShouldSpec({
    val formatter = ElementFormatter(indentSize = 4)
    val ___4 = " ".repeat(4) // 1st position indentation
    val _______8 = " ".repeat(8) // 2nd position indentation
    val __________12 = " ".repeat(12) // 3rd position indentation

    context("list expression formatter") {

        context("NEW LINE mode") {

            should("format empty list") {
                val list = ListExpression(emptyList())

                val builder = StringBuilder()
                formatter.visit(list, position = 1, NEW_LINE, builder)

                val expectedResult = "${___4}[]"

                builder.toString() shouldBe expectedResult
            }

            should("format single item string list") {
                val list = ListExpression(listOf(StringLiteral("item")))

                val builder = StringBuilder()
                formatter.visit(list, position = 1, NEW_LINE, builder)

                val expectedResult = """
                    |${___4}["item"]
                """.trimMargin()

                builder.toString() shouldBe expectedResult
            }

            should("format string list") {
                val list = ListExpression(listOf(StringLiteral("item1"), StringLiteral("item2")))

                val builder = StringBuilder()
                formatter.visit(list, position = 1, NEW_LINE, builder)

                val expectedResult = """
                    |${___4}[
                    |${_______8}"item1",
                    |${_______8}"item2",
                    |${___4}]
                """.trimMargin()

                builder.toString() shouldBe expectedResult
            }

            should("format list of lists") {
                val list = ListExpression(
                    listOf(
                        ListExpression(listOf()),
                        ListExpression(listOf(StringLiteral("item1"))),
                        ListExpression(listOf(StringLiteral("item2"), StringLiteral("item3"))),
                    )
                )

                val builder = StringBuilder()
                formatter.visit(list, position = 1, NEW_LINE, builder)

                val expectedResult = """
                    |${___4}[
                    |${_______8}[],
                    |${_______8}["item1"],
                    |${_______8}[
                    |${__________12}"item2",
                    |${__________12}"item3",
                    |${_______8}],
                    |${___4}]
                """.trimMargin()

                builder.toString() shouldBe expectedResult
            }

            should("format single item list of lists") {
                val list = ListExpression(
                    listOf(ListExpression(listOf(StringLiteral("item1"), StringLiteral("item2"))))
                )

                val builder = StringBuilder()
                formatter.visit(list, position = 1, NEW_LINE, builder)

                val expectedResult = """
                    |${___4}[[
                    |${_______8}"item1",
                    |${_______8}"item2",
                    |${___4}]]
                """.trimMargin()

                builder.toString() shouldBe expectedResult
            }
        }

        context("CONTINUE LINE mode") {
            should("format empty string list") {
                val list = ListExpression(emptyList())

                val builder = StringBuilder()
                formatter.visit(list, position = 1, CONTINUE_LINE, builder)

                val expectedResult = "[]"

                builder.toString() shouldBe expectedResult
            }

            should("format single item string list") {
                val list = ListExpression(listOf(StringLiteral("item")))

                val builder = StringBuilder()
                formatter.visit(list, position = 1, CONTINUE_LINE, builder)

                val expectedResult = """
                    |["item"]
                """.trimMargin()

                builder.toString() shouldBe expectedResult
            }

            should("format string list") {
                val list = ListExpression(listOf(StringLiteral("item1"), StringLiteral("item2")))

                val builder = StringBuilder()
                formatter.visit(list, position = 1, CONTINUE_LINE, builder)

                val expectedResult = """
                    |[
                    |${_______8}"item1",
                    |${_______8}"item2",
                    |${___4}]
                """.trimMargin()

                builder.toString() shouldBe expectedResult
            }
        }
    }
})