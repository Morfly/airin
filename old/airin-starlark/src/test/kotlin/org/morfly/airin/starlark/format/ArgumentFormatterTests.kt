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

import org.morfly.airin.starlark.elements.Argument
import org.morfly.airin.starlark.elements.ListExpression
import org.morfly.airin.starlark.elements.PositionMode.CONTINUE_LINE
import org.morfly.airin.starlark.elements.PositionMode.NEW_LINE
import org.morfly.airin.starlark.elements.StringLiteral
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