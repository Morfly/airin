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

import org.morfly.airin.starlark.elements.*
import org.morfly.airin.starlark.elements.PositionMode.CONTINUE_LINE
import org.morfly.airin.starlark.elements.PositionMode.NEW_LINE
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe


class AssignmentFormatterTests : ShouldSpec({
    val formatter = StarlarkCodeFormatter(indentSize = 4)
    val ___4 = " ".repeat(4) // 1st position indentation
    val _______8 = " ".repeat(8) // 2nd position indentation

    context("assignment statements formatter") {

        should("throw exception when formatting assignments as continuation") {
            val assignment = Assignment("VARIABLE", StringLiteral("value"))

            val builder = StringBuilder()
            shouldThrow<IllegalArgumentException> {
                formatter.visit(assignment, position = 1, mode = CONTINUE_LINE, builder)
            }
        }

        should("format null assignment") {
            val assignment = Assignment("VARIABLE", value = null)

            val builder = StringBuilder()
            formatter.visit(assignment, position = 1, mode = NEW_LINE, builder)

            val expectedResult = "\n${___4}VARIABLE = None"

            builder.toString() shouldBe expectedResult
        }

        should("format string literal assignments") {
            val assignment = Assignment("VARIABLE", StringLiteral("value"))

            val builder = StringBuilder()
            formatter.visit(assignment, position = 1, mode = NEW_LINE, builder)

            val expectedResult = """
                |
                |${___4}VARIABLE = "value"
            """.trimMargin()

            builder.toString() shouldBe expectedResult
        }

        should("format integer literal assignments") {
            val assignment = Assignment("VARIABLE", IntegerLiteral(1))

            val builder = StringBuilder()
            formatter.visit(assignment, position = 1, mode = NEW_LINE, builder)

            val expectedResult = """
                |
                |${___4}VARIABLE = 1
            """.trimMargin()

            builder.toString() shouldBe expectedResult
        }

        should("format floating point literal assignments") {
            val assignment = Assignment("VARIABLE", FloatLiteral(1.5))

            val builder = StringBuilder()
            formatter.visit(assignment, position = 1, mode = NEW_LINE, builder)

            val expectedResult = """
                |
                |${___4}VARIABLE = 1.5
            """.trimMargin()

            builder.toString() shouldBe expectedResult
        }

        should("format boolean literal assignments") {
            val assignment = Assignment("VARIABLE", BooleanLiteral(true))

            val builder = StringBuilder()
            formatter.visit(assignment, position = 1, mode = NEW_LINE, builder)

            val expectedResult = """
                |
                |${___4}VARIABLE = True
            """.trimMargin()

            builder.toString() shouldBe expectedResult
        }

        should("format new empty list assignments") {
            val assignment = Assignment("VARIABLE", ListExpression<Any>(emptyList()))

            val builder = StringBuilder()
            formatter.visit(assignment, position = 1, mode = NEW_LINE, builder)

            val expectedResult = "\n${___4}VARIABLE = []"

            builder.toString() shouldBe expectedResult
        }

        should("format new single item list assignments") {
            val assignment = Assignment(
                name = "VARIABLE",
                value = ListExpression<Any>(listOf(StringLiteral("item")))
            )

            val builder = StringBuilder()
            formatter.visit(assignment, position = 1, mode = NEW_LINE, builder)

            val expectedResult = """
                |
                |${___4}VARIABLE = ["item"]
            """.trimMargin()

            builder.toString() shouldBe expectedResult
        }

        should("format new list assignments") {
            val assignment = Assignment(
                name = "VARIABLE",
                value = ListExpression<Any>(listOf(StringLiteral("item1"), StringLiteral("item2")))
            )

            val builder = StringBuilder()
            formatter.visit(assignment, position = 1, mode = NEW_LINE, builder)

            val expectedResult = """
                |
                |${___4}VARIABLE = [
                |${_______8}"item1",
                |${_______8}"item2",
                |${___4}]
            """.trimMargin()

            builder.toString() shouldBe expectedResult
        }

        should("format empty dictionary assignments") {
            val assignment = Assignment("VARIABLE", DictionaryExpression(emptyMap()))

            val builder = StringBuilder()
            formatter.visit(assignment, position = 1, mode = NEW_LINE, builder)

            val expectedResult = "\n${___4}VARIABLE = {}"

            builder.toString() shouldBe expectedResult
        }

        should("format new single item dictionary assignments") {
            val assignment = Assignment(
                name = "VARIABLE",
                value = DictionaryExpression(mapOf(StringLiteral("key") to StringLiteral("value")))
            )

            val builder = StringBuilder()
            formatter.visit(assignment, position = 1, mode = NEW_LINE, builder)

            val expectedResult = """
                |
                |${___4}VARIABLE = {"key": "value"}
            """.trimMargin()

            builder.toString() shouldBe expectedResult
        }

        should("format new dictionary assignments") {
            val assignment = Assignment(
                name = "VARIABLE",
                value = DictionaryExpression(
                    mapOf(
                        StringLiteral("key1") to StringLiteral("value1"),
                        StringLiteral("key2") to IntegerLiteral(2)
                    )
                )
            )

            val builder = StringBuilder()
            formatter.visit(assignment, position = 1, mode = NEW_LINE, builder)

            val expectedResult = """
                |
                |${___4}VARIABLE = {
                |${_______8}"key1": "value1",
                |${_______8}"key2": 2,
                |${___4}}
            """.trimMargin()

            builder.toString() shouldBe expectedResult
        }

        should("format reference assignments") {
            val assignment = Assignment("VARIABLE", StringReference("ANOTHER_VARIABLE"))

            val builder = StringBuilder()
            formatter.visit(assignment, position = 1, mode = NEW_LINE, builder)

            val expectedResult = "\n${___4}VARIABLE = ANOTHER_VARIABLE"

            builder.toString() shouldBe expectedResult
        }

        should("format concatenated references assignment") {
            val stringRefConcatenation = StringBinaryOperation(
                left = StringReference("VARIABLE_1"),
                operator = BinaryOperator.PLUS,
                right = StringReference("VARIABLE_2")
            )
            val assignment = Assignment(
                name = "VARIABLE_3",
                value = stringRefConcatenation
            )

            val builder = StringBuilder()
            formatter.visit(assignment, position = 1, mode = NEW_LINE, builder)

            val expectedResult = """
                |
                |${___4}VARIABLE_3 = VARIABLE_1 + VARIABLE_2
            """.trimMargin()

            builder.toString() shouldBe expectedResult
        }

        should("format concatenated string assignments") {
            val stringConcatenation = StringBinaryOperation(
                left = StringLiteral("string1"),
                operator = BinaryOperator.PLUS,
                right = StringLiteral("string2")
            )
            val assignment = Assignment(
                name = "VARIABLE",
                value = stringConcatenation
            )

            val builder = StringBuilder()
            formatter.visit(assignment, position = 1, mode = NEW_LINE, builder)

            val expectedResult = """
                |
                |${___4}VARIABLE = "string1" + "string2"
            """.trimMargin()

            builder.toString() shouldBe expectedResult
        }

        should("format concatenated list assignments") {
            val listConcatenation = ListBinaryOperation<String>(
                left = ListExpression<Any>(listOf(StringLiteral("item1"), StringLiteral("item2"))),
                operator = BinaryOperator.PLUS,
                right = ListExpression<Any>(listOf(StringLiteral("item3"), StringLiteral("item4")))
            )
            val assignment = Assignment(
                name = "VARIABLE",
                value = listConcatenation
            )

            val builder = StringBuilder()
            formatter.visit(assignment, position = 1, mode = NEW_LINE, builder)

            val expectedResult = """
                |
                |${___4}VARIABLE = [
                |${_______8}"item1",
                |${_______8}"item2",
                |${___4}] + [
                |${_______8}"item3",
                |${_______8}"item4",
                |${___4}]
            """.trimMargin()

            builder.toString() shouldBe expectedResult
        }

        should("format concatenated dictionary assignments") {
            val dictConcatenation = DictionaryBinaryOperation<Any?, Any?>(
                left = DictionaryExpression(mapOf(StringLiteral("key1") to StringLiteral("value1"))),
                operator = BinaryOperator.PLUS,
                right = DictionaryExpression(
                    mapOf(
                        StringLiteral("key2") to StringLiteral("value2"),
                        StringLiteral("key3") to IntegerLiteral(3)
                    )
                )
            )
            val assignment = Assignment(
                name = "VARIABLE",
                value = dictConcatenation
            )

            val builder = StringBuilder()
            formatter.visit(assignment, position = 1, mode = NEW_LINE, builder)

            val expectedResult = """
                |
                |${___4}VARIABLE = {"key1": "value1"} + {
                |${_______8}"key2": "value2",
                |${_______8}"key3": 3,
                |${___4}}
            """.trimMargin()

            builder.toString() shouldBe expectedResult
        }
    }
})