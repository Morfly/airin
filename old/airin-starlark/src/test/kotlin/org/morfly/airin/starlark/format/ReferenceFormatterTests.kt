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

import org.morfly.airin.starlark.elements.AnyReference
import org.morfly.airin.starlark.elements.DictionaryReference
import org.morfly.airin.starlark.elements.ListReference
import org.morfly.airin.starlark.elements.PositionMode.CONTINUE_LINE
import org.morfly.airin.starlark.elements.PositionMode.NEW_LINE
import org.morfly.airin.starlark.elements.StringReference
import org.morfly.airin.starlark.lang.Key
import org.morfly.airin.starlark.lang.StringType
import org.morfly.airin.starlark.lang.Value
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe


class ReferenceFormatterTests : ShouldSpec({
    val formatter = StarlarkCodeFormatter(indentSize = 4)
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