@file:Suppress("LocalVariableName")

package com.morfly.airin.starlark.format

import com.morlfy.airin.starlark.elements.Argument
import com.morlfy.airin.starlark.elements.ListExpression
import com.morlfy.airin.starlark.elements.PositionMode.CONTINUE_LINE
import com.morlfy.airin.starlark.elements.PositionMode.NEW_LINE
import com.morlfy.airin.starlark.elements.StringLiteral
import com.morlfy.airin.starlark.format.StarlarkCodeFormatter
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe


class ArgumentFormatterTests : ShouldSpec({
    val formatter = StarlarkCodeFormatter(indentSize = 4)
    val ___4 = " ".repeat(4) // 1st position indentation
    val _______8 = " ".repeat(8) // 2nd position indentation

    context("argument formatter") {

        context("NEW LINE mode") {

            should("format string literal argument") {
                val argument = Argument(id = "arg", value = StringLiteral("value"))

                val builder = StringBuilder()
                formatter.visit(argument, position = 1, mode = NEW_LINE, builder)

                val expectedResult = """
                    |${___4}arg = "value"
                """.trimMargin()

                builder.toString() shouldBe expectedResult
            }

            should("format multiline list argument") {
                val argument = Argument(id = "arg", value = ListExpression(listOf("item1", "item2")))

                val builder = StringBuilder()
                formatter.visit(argument, position = 1, mode = NEW_LINE, builder)

                val expectedResult = """
                    |${___4}arg = [
                    |${_______8}"item1",
                    |${_______8}"item2",
                    |${___4}]
                """.trimMargin()

                builder.toString() shouldBe expectedResult
            }

            should("format argument without specified id") {
                val argument = Argument(id = "", value = StringLiteral("value"))

                val builder = StringBuilder()
                formatter.visit(argument, position = 1, mode = NEW_LINE, builder)

                val expectedResult = """
                    |${___4}"value"
                """.trimMargin()

                builder.toString() shouldBe expectedResult
            }
        }

        context("CONTINUE LINE mode") {

            should("format multiline list argument") {
                val argument = Argument(id = "arg", value = ListExpression(listOf("item1", "item2")))

                val builder = StringBuilder()
                formatter.visit(argument, position = 1, mode = CONTINUE_LINE, builder)

                val expectedResult = """
                    |arg = [
                    |${_______8}"item1",
                    |${_______8}"item2",
                    |${___4}]
                """.trimMargin()

                builder.toString() shouldBe expectedResult
            }
        }
    }
})