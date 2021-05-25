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

import com.morlfy.airin.starlark.elements.*
import com.morlfy.airin.starlark.elements.PositionMode.CONTINUE_LINE
import com.morlfy.airin.starlark.elements.PositionMode.NEW_LINE
import com.morlfy.airin.starlark.format.StarlarkCodeFormatter
import com.morlfy.airin.starlark.lang.StringType
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe


class ListComprehensionFormatterTests : ShouldSpec({
    val formatter = StarlarkCodeFormatter(indentSize = 4)
    val ___4 = " ".repeat(4) // 1st position indentation
    val _______8 = " ".repeat(8) // 2nd position indentation
    val __________12 = " ".repeat(12) // 3rd position indentation

    context("list comprehensions formatter") {

        context("NEW LINE mode") {

            should("format comprehension from list reference as iterable") {
                val iterable = ListReference<StringType>(name = "LIST")
                val itemReference = StringReference(name = "item")
                val comprehension = ListComprehension<StringType>(
                    body = itemReference,
                    clauses = mutableListOf(
                        Comprehension.For(variables = listOf(itemReference), iterable)
                    )
                )

                val builder = StringBuilder()
                formatter.visit(comprehension, position = 1, NEW_LINE, builder)

                val expectedResult = "${___4}[item for item in LIST]"

                builder.toString() shouldBe expectedResult
            }

            should("format comprehension from list reference as iterable, with condition") {
                val iterable = ListReference<StringType>(name = "LIST")
                val itemReference = StringReference(name = "item")
                val comprehension = ListComprehension<StringType>(
                    body = itemReference,
                    clauses = mutableListOf(
                        Comprehension.For(variables = listOf(itemReference), iterable),
                        Comprehension.If(condition = AnyRawExpression("condition"))
                    )
                )

                val builder = StringBuilder()
                formatter.visit(comprehension, position = 1, NEW_LINE, builder)

                val expectedResult = """
                    |${___4}[item for item in LIST if condition]
                """.trimMargin()

                builder.toString() shouldBe expectedResult
            }

            should("format comprehension from 1 item list initialized in place") {
                val iterable = ListExpression(listOf("item1"))
                val itemReference = StringReference(name = "item")
                val comprehension = ListComprehension<StringType>(
                    body = itemReference,
                    clauses = mutableListOf(
                        Comprehension.For(variables = listOf(itemReference), iterable)
                    )
                )

                val builder = StringBuilder()
                formatter.visit(comprehension, position = 1, NEW_LINE, builder)

                val expectedResult = """
                    |${___4}[item for item in ["item1"]]
                """.trimMargin()

                builder.toString() shouldBe expectedResult
            }

            should("format comprehension from 2D list reference as iterable") {
                val iterable = ListReference<List<StringType>>(name = "MATRIX")
                val sublistReference = ListReference<StringType>(name = "sublist")
                val itemReference = StringReference(name = "item")
                val comprehension = ListComprehension<StringType>(
                    body = itemReference,
                    clauses = mutableListOf(
                        Comprehension.For(variables = listOf(sublistReference), iterable),
                        Comprehension.For(variables = listOf(itemReference), sublistReference)
                    )
                )

                val builder = StringBuilder()
                formatter.visit(comprehension, position = 1, NEW_LINE, builder)

                val expectedResult = "${___4}[item for sublist in MATRIX for item in sublist]"

                builder.toString() shouldBe expectedResult
            }

            should("format comprehension from 2D list reference as iterable, taking list as item") {
                val iterable = ListReference<List<StringType>>(name = "MATRIX")
                val sublistReference = ListReference<StringType>(name = "sublist")
                val itemReference = StringReference(name = "item")
                val comprehension = ListComprehension<StringType>(
                    body = sublistReference,
                    clauses = mutableListOf(
                        Comprehension.For(variables = listOf(sublistReference), iterable),
                        Comprehension.For(variables = listOf(itemReference), sublistReference)
                    )
                )

                val builder = StringBuilder()
                formatter.visit(comprehension, position = 1, NEW_LINE, builder)

                val expectedResult = "${___4}[sublist for sublist in MATRIX for item in sublist]"

                builder.toString() shouldBe expectedResult
            }

            should("format comprehension taking another comprehension for each item") {
                val iterable = ListReference<StringType>(name = "LIST")
                val iReference = StringReference(name = "i")
                val jReference = StringReference(name = "j")
                val nestedComprehension = ListComprehension<StringType>(
                    body = jReference,
                    clauses = mutableListOf(
                        Comprehension.For(variables = listOf(jReference), iterable)
                    )
                )
                val comprehension = ListComprehension<ListComprehension<StringType>>(
                    body = nestedComprehension,
                    clauses = mutableListOf(
                        Comprehension.For(variables = listOf(iReference), iterable)
                    )
                )

                val builder = StringBuilder()
                formatter.visit(comprehension, position = 1, NEW_LINE, builder)

                val expectedResult = """
                    |${___4}[
                    |${_______8}[j for j in LIST]
                    |${_______8}for i in LIST
                    |${___4}]
                """.trimMargin()

                builder.toString() shouldBe expectedResult
            }

            should("format comprehension taking another nested comprehension for each item") {
                val iterable = ListReference<StringType>(name = "LIST")
                val iReference = StringReference(name = "i")
                val jReference = StringReference(name = "j")
                val kReference = StringReference(name = "k")
                val nestedComprehension1 = ListComprehension<StringType>(
                    body = kReference,
                    clauses = mutableListOf(
                        Comprehension.For(variables = listOf(kReference), iterable)
                    )
                )
                val nestedComprehension2 = ListComprehension<StringType>(
                    body = nestedComprehension1,
                    clauses = mutableListOf(
                        Comprehension.For(variables = listOf(jReference), iterable)
                    )
                )
                val comprehension = ListComprehension<ListComprehension<StringType>>(
                    body = nestedComprehension2,
                    clauses = mutableListOf(
                        Comprehension.For(variables = listOf(iReference), iterable)
                    )
                )

                val builder = StringBuilder()
                formatter.visit(comprehension, position = 1, NEW_LINE, builder)

                val expectedResult = """
                    |${___4}[
                    |${_______8}[
                    |${__________12}[k for k in LIST]
                    |${__________12}for j in LIST
                    |${_______8}]
                    |${_______8}for i in LIST
                    |${___4}]
                """.trimMargin()

                builder.toString() shouldBe expectedResult
            }
        }

        context("CONTINUE LINE mode") {

            should("format properly single line comprehension in CONTINUE_LINE mode") {
                val iterable = ListReference<StringType>(name = "LIST")
                val itemReference = StringReference(name = "item")
                val comprehension = ListComprehension<StringType>(
                    body = itemReference,
                    clauses = mutableListOf(
                        Comprehension.For(variables = listOf(itemReference), iterable)
                    )
                )

                val builder = StringBuilder()
                formatter.visit(comprehension, position = 1, CONTINUE_LINE, builder)

                val expectedResult = "[item for item in LIST]"

                builder.toString() shouldBe expectedResult
            }

            should("format properly multiline comprehension in CONTINUE_LINE mode") {
                val iterable = ListReference<StringType>(name = "LIST")
                val iReference = StringReference(name = "i")
                val jReference = StringReference(name = "j")
                val nestedComprehension = ListComprehension<StringType>(
                    body = jReference,
                    clauses = mutableListOf(
                        Comprehension.For(variables = listOf(jReference), iterable)
                    )
                )
                val comprehension = ListComprehension<ListComprehension<StringType>>(
                    body = nestedComprehension,
                    clauses = mutableListOf(
                        Comprehension.For(variables = listOf(iReference), iterable)
                    )
                )

                val builder = StringBuilder()
                formatter.visit(comprehension, position = 1, CONTINUE_LINE, builder)

                val expectedResult = """
                    |[
                    |${_______8}[j for j in LIST]
                    |${_______8}for i in LIST
                    |${___4}]
                """.trimMargin()

                builder.toString() shouldBe expectedResult
            }
        }
    }
})

