@file:Suppress("LocalVariableName")

package com.morfly.airin.starlark.format

import com.morlfy.airin.starlark.elements.Expression
import com.morlfy.airin.starlark.elements.PositionMode.CONTINUE_LINE
import com.morlfy.airin.starlark.elements.PositionMode.NEW_LINE
import com.morlfy.airin.starlark.format.ElementFormatter
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe


class ExpressionFormatterTests : ShouldSpec({
    val formatter = ElementFormatter(indentSize = 4)
    val ___4 = " ".repeat(4) // 1st position indentation

    context("expression formatter") {

        context("NEW LINE mode") {

            should("format null as expression") {
                val none: Expression? = null

                val builder = StringBuilder()
                formatter.visit(none, position = 1, NEW_LINE, builder)

                val expectedResult = "${___4}None"

                builder.toString() shouldBe expectedResult
            }
        }

        context("CONTINUE LINE mode") {

            should("format null as expression") {
                val none: Expression? = null

                val builder = StringBuilder()
                formatter.visit(none, position = 1, CONTINUE_LINE, builder)

                val expectedResult = "None"

                builder.toString() shouldBe expectedResult
            }
        }
    }
})