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

import org.morfly.airin.starlark.elements.DictionaryExpression
import org.morfly.airin.starlark.elements.DynamicExpression
import org.morfly.airin.starlark.elements.ListExpression
import org.morfly.airin.starlark.elements.PositionMode.CONTINUE_LINE
import org.morfly.airin.starlark.elements.PositionMode.NEW_LINE
import org.morfly.airin.starlark.elements.StringLiteral
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe


class DictionaryExpressionFormatterTests : ShouldSpec({
    val formatter = StarlarkCodeFormatter(indentSize = 4)
    val ___4 = " ".repeat(4) // 1st position indentation
    val _______8 = " ".repeat(8) // 2nd position indentation
    val __________12 = " ".repeat(12) // 3rd position indentation

    context("dictionary expression formatter") {

        context("NEW LINE mode") {

            should("format empty dictionary") {
                val dict = DictionaryExpression(emptyMap())

                val builder = StringBuilder()
                formatter.visit(dict, position = 1, NEW_LINE, builder)

                val expectedResult = """
                    |${___4}{}
                """.trimMargin()

                builder.toString() shouldBe expectedResult
            }

            should("format single item dictionary") {
                val dict = DictionaryExpression(mapOf(StringLiteral("key") to DynamicExpression(StringLiteral("value"))))

                val builder = StringBuilder()
                formatter.visit(dict, position = 1, NEW_LINE, builder)

                val expectedResult = """
                    |${___4}{"key": "value"}
                """.trimMargin()

                builder.toString() shouldBe expectedResult
            }

            should("format dictionary") {
                val dict = DictionaryExpression(
                    mapOf(
                        StringLiteral("key1") to DynamicExpression(StringLiteral("value1")),
                        StringLiteral("key2") to DynamicExpression(StringLiteral("value2")),
                    )
                )

                val builder = StringBuilder()
                formatter.visit(dict, position = 1, NEW_LINE, builder)

                val expectedResult = """
                    |${___4}{
                    |${_______8}"key1": "value1",
                    |${_______8}"key2": "value2",
                    |${___4}}
                """.trimMargin()

                builder.toString() shouldBe expectedResult
            }

            should("format dictionary of lists") {
                val dict = DictionaryExpression(
                    mapOf(
                        StringLiteral("key1") to DynamicExpression(ListExpression<Any>(emptyList())),
                        StringLiteral("key2") to DynamicExpression(ListExpression<Any>(listOf(StringLiteral("item1")))),
                        StringLiteral("key3") to DynamicExpression(
                            ListExpression<Any>(listOf(StringLiteral("item2"), StringLiteral("item3")))
                        ),
                    )
                )

                val builder = StringBuilder()
                formatter.visit(dict, position = 1, NEW_LINE, builder)

                val expectedResult = """
                    |${___4}{
                    |${_______8}"key1": [],
                    |${_______8}"key2": ["item1"],
                    |${_______8}"key3": [
                    |${__________12}"item2",
                    |${__________12}"item3",
                    |${_______8}],
                    |${___4}}
                """.trimMargin()

                builder.toString() shouldBe expectedResult
            }

            should("format single item dictionary of dictionaries") {
                val dict = DictionaryExpression(
                    mapOf(
                        StringLiteral("key1") to DynamicExpression(
                            DictionaryExpression(
                                mapOf(
                                    StringLiteral("key2") to DynamicExpression(StringLiteral("value2")),
                                    StringLiteral("key3") to DynamicExpression(StringLiteral("value3"))
                                )
                            )
                        ),
                    )
                )

                val builder = StringBuilder()
                formatter.visit(dict, position = 1, NEW_LINE, builder)

                val expectedResult = """
                    |${___4}{"key1": {
                    |${_______8}"key2": "value2",
                    |${_______8}"key3": "value3",
                    |${___4}}}
                """.trimMargin()

                builder.toString() shouldBe expectedResult
            }

            should("format single item dictionary with multiline key") {
                val dict = DictionaryExpression(
                    mapOf(
                        ListExpression<Any>(listOf(StringLiteral("key1"), StringLiteral("key2"))) to
                                DynamicExpression(StringLiteral("value"))
                    )
                )

                val builder = StringBuilder()
                formatter.visit(dict, position = 1, NEW_LINE, builder)

                val expectedResult = """
                    |${___4}{[
                    |${_______8}"key1",
                    |${_______8}"key2",
                    |${___4}]: "value"}
                """.trimMargin()

                builder.toString() shouldBe expectedResult
            }

            should("format single item dictionary with multiline key and value") {
                val dict = DictionaryExpression(
                    mapOf(
                        ListExpression<Any>(listOf(StringLiteral("key1"), StringLiteral("key2"))) to DynamicExpression(
                            ListExpression<Any>(listOf(StringLiteral("item1"), StringLiteral("item2")))
                        )
                    )
                )

                val builder = StringBuilder()
                formatter.visit(dict, position = 1, NEW_LINE, builder)

                val expectedResult = """
                    |${___4}{[
                    |${_______8}"key1",
                    |${_______8}"key2",
                    |${___4}]: [
                    |${_______8}"item1",
                    |${_______8}"item2",
                    |${___4}]}
                """.trimMargin()

                builder.toString() shouldBe expectedResult
            }

            should("format dictionary with multiline keys and values") {
                val dict = DictionaryExpression(
                    mapOf(
                        ListExpression<Any>(listOf(StringLiteral("key1"), StringLiteral("key2"))) to DynamicExpression(
                            ListExpression<Any>(listOf(StringLiteral("item1"), StringLiteral("item2")))
                        ),
                        ListExpression<Any>(listOf(StringLiteral("key3"), StringLiteral("key4"))) to DynamicExpression(
                            ListExpression<Any>(listOf(StringLiteral("item3"), StringLiteral("item4")))
                        )
                    )
                )

                val builder = StringBuilder()
                formatter.visit(dict, position = 1, NEW_LINE, builder)

                val expectedResult = """
                    |${___4}{
                    |${_______8}[
                    |${__________12}"key1",
                    |${__________12}"key2",
                    |${_______8}]: [
                    |${__________12}"item1",
                    |${__________12}"item2",
                    |${_______8}],
                    |${_______8}[
                    |${__________12}"key3",
                    |${__________12}"key4",
                    |${_______8}]: [
                    |${__________12}"item3",
                    |${__________12}"item4",
                    |${_______8}],
                    |${___4}}
                """.trimMargin()

                builder.toString() shouldBe expectedResult
            }
        }

        context("CONTINUE LINE mode") {

            should("format empty dictionary") {
                val dict = DictionaryExpression(emptyMap())

                val builder = StringBuilder()
                formatter.visit(dict, position = 1, CONTINUE_LINE, builder)

                val expectedResult = """
                    |{}
                """.trimMargin()

                builder.toString() shouldBe expectedResult
            }

            should("format single item dictionary") {
                val dict = DictionaryExpression(mapOf(StringLiteral("key") to DynamicExpression(StringLiteral("value"))))

                val builder = StringBuilder()
                formatter.visit(dict, position = 1, CONTINUE_LINE, builder)

                val expectedResult = """
                    |{"key": "value"}
                """.trimMargin()

                builder.toString() shouldBe expectedResult
            }

            should("format dictionary") {
                val dict = DictionaryExpression(
                    mapOf(
                        StringLiteral("key1") to DynamicExpression(StringLiteral("value1")),
                        StringLiteral("key2") to DynamicExpression(StringLiteral("value2")),
                    )
                )

                val builder = StringBuilder()
                formatter.visit(dict, position = 1, CONTINUE_LINE, builder)

                val expectedResult = """
                    |{
                    |${_______8}"key1": "value1",
                    |${_______8}"key2": "value2",
                    |${___4}}
                """.trimMargin()

                builder.toString() shouldBe expectedResult
            }
        }
    }
})