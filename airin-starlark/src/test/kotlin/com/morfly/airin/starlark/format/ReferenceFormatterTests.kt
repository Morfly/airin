@file:Suppress("LocalVariableName")

package com.morfly.airin.starlark.format

import com.morlfy.airin.starlark.elements.AnyReference
import com.morlfy.airin.starlark.elements.DictionaryReference
import com.morlfy.airin.starlark.elements.ListReference
import com.morlfy.airin.starlark.elements.PositionMode.CONTINUE_LINE
import com.morlfy.airin.starlark.elements.PositionMode.NEW_LINE
import com.morlfy.airin.starlark.elements.StringReference
import com.morlfy.airin.starlark.format.ElementFormatter
import com.morlfy.airin.starlark.lang.Key
import com.morlfy.airin.starlark.lang.StringType
import com.morlfy.airin.starlark.lang.Value
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe


class ReferenceFormatterTests : ShouldSpec({
    val formatter = ElementFormatter(indentSize = 4)
    val ___4 = " ".repeat(4) // 1st position indentation

    context("reference formatter tests") {

        context("NEW LINE mode") {

            should("should format string reference") {
                val reference = StringReference(name = "VARIABLE")

                val builder = StringBuilder()
                formatter.visit(reference, position = 1, NEW_LINE, builder)

                val expectedResult = "${___4}VARIABLE"

                builder.toString() shouldBe expectedResult
            }

            should("should format list reference") {
                val reference = ListReference<StringType>(name = "VARIABLE")

                val builder = StringBuilder()
                formatter.visit(reference, position = 1, NEW_LINE, builder)

                val expectedResult = "${___4}VARIABLE"

                builder.toString() shouldBe expectedResult
            }

            should("should format dictionary reference") {
                val reference = DictionaryReference<Key, Value>(name = "VARIABLE")

                val builder = StringBuilder()
                formatter.visit(reference, position = 1, NEW_LINE, builder)

                val expectedResult = "${___4}VARIABLE"

                builder.toString() shouldBe expectedResult
            }

            should("should format any reference") {
                val reference = AnyReference(name = "VARIABLE")

                val builder = StringBuilder()
                formatter.visit(reference, position = 1, NEW_LINE, builder)

                val expectedResult = "${___4}VARIABLE"

                builder.toString() shouldBe expectedResult
            }
        }

        context("CONTINUE LINE mode") {

            should("should format string reference") {
                val reference = StringReference(name = "VARIABLE")

                val builder = StringBuilder()
                formatter.visit(reference, position = 1, CONTINUE_LINE, builder)

                val expectedResult = "VARIABLE"

                builder.toString() shouldBe expectedResult
            }

            should("should format list reference") {
                val reference = ListReference<StringType>(name = "VARIABLE")

                val builder = StringBuilder()
                formatter.visit(reference, position = 1, CONTINUE_LINE, builder)

                val expectedResult = "VARIABLE"

                builder.toString() shouldBe expectedResult
            }

            should("should format dictionary reference") {
                val reference = DictionaryReference<Key, Value>(name = "VARIABLE")

                val builder = StringBuilder()
                formatter.visit(reference, position = 1, CONTINUE_LINE, builder)

                val expectedResult = "VARIABLE"

                builder.toString() shouldBe expectedResult
            }

            should("should format any reference") {
                val reference = AnyReference(name = "VARIABLE")

                val builder = StringBuilder()
                formatter.visit(reference, position = 1, CONTINUE_LINE, builder)

                val expectedResult = "VARIABLE"

                builder.toString() shouldBe expectedResult
            }
        }
    }
})