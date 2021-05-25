@file:Suppress("LocalVariableName")

package com.morfly.airin.starlark.format

import com.morlfy.airin.starlark.elements.DictionaryExpression
import com.morlfy.airin.starlark.elements.DynamicValue
import com.morlfy.airin.starlark.elements.ListExpression
import com.morlfy.airin.starlark.elements.PositionMode.CONTINUE_LINE
import com.morlfy.airin.starlark.elements.PositionMode.NEW_LINE
import com.morlfy.airin.starlark.elements.StringLiteral
import com.morlfy.airin.starlark.format.StarlarkCodeFormatter
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
                val dict = DictionaryExpression(mapOf(StringLiteral("key") to DynamicValue(StringLiteral("value"))))

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
                        StringLiteral("key1") to DynamicValue(StringLiteral("value1")),
                        StringLiteral("key2") to DynamicValue(StringLiteral("value2")),
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
                        StringLiteral("key1") to DynamicValue(ListExpression(emptyList())),
                        StringLiteral("key2") to DynamicValue(ListExpression(listOf(StringLiteral("item1")))),
                        StringLiteral("key3") to
                                DynamicValue(ListExpression(listOf(StringLiteral("item2"), StringLiteral("item3")))),
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
                        StringLiteral("key1") to DynamicValue(
                            DictionaryExpression(
                                mapOf(
                                    StringLiteral("key2") to DynamicValue(StringLiteral("value2")),
                                    StringLiteral("key3") to DynamicValue(StringLiteral("value3"))
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
                        ListExpression(listOf(StringLiteral("key1"), StringLiteral("key2"))) to
                                DynamicValue(StringLiteral("value"))
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
                        ListExpression(listOf(StringLiteral("key1"), StringLiteral("key2"))) to
                                DynamicValue(ListExpression(listOf(StringLiteral("item1"), StringLiteral("item2"))))
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
                        ListExpression(listOf(StringLiteral("key1"), StringLiteral("key2"))) to
                                DynamicValue(ListExpression(listOf(StringLiteral("item1"), StringLiteral("item2")))),
                        ListExpression(listOf(StringLiteral("key3"), StringLiteral("key4"))) to
                                DynamicValue(ListExpression(listOf(StringLiteral("item3"), StringLiteral("item4"))))
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
                val dict = DictionaryExpression(mapOf(StringLiteral("key") to DynamicValue(StringLiteral("value"))))

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
                        StringLiteral("key1") to DynamicValue(StringLiteral("value1")),
                        StringLiteral("key2") to DynamicValue(StringLiteral("value2")),
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