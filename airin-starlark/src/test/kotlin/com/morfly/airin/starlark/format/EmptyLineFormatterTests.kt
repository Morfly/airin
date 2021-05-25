package com.morfly.airin.starlark.format

import com.morlfy.airin.starlark.elements.EmptyLineStatement
import com.morlfy.airin.starlark.elements.PositionMode.*
import com.morlfy.airin.starlark.format.StarlarkCodeFormatter
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe


class EmptyLineFormatterTests : ShouldSpec({
    val formatter = StarlarkCodeFormatter(indentSize = 4)

    context("empty line formatter") {

        should("format empty line statement") {
            val statement = EmptyLineStatement

            val builder1 = StringBuilder()
            formatter.visit(statement, position = 1, NEW_LINE, builder1)
            val builder2 = StringBuilder()
            formatter.visit(statement, position = 1, CONTINUE_LINE, builder2)
            val builder3 = StringBuilder()
            formatter.visit(statement, position = 1, SINGLE_LINE, builder3)

            val expectedResult = "\n"

            builder1.toString() shouldBe expectedResult
            builder2.toString() shouldBe expectedResult
            builder3.toString() shouldBe expectedResult
        }
    }
})