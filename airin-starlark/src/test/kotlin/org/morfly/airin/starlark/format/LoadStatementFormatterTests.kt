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

import org.morfly.airin.starlark.elements.LoadStatement
import org.morfly.airin.starlark.elements.PositionMode.CONTINUE_LINE
import org.morfly.airin.starlark.elements.PositionMode.NEW_LINE
import org.morfly.airin.starlark.elements.StringLiteral
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe


class LoadStatementFormatterTests : ShouldSpec({
    val formatter = StarlarkCodeFormatter(indentSize = 4)
    val ___4 = " ".repeat(4) // 1st position indentation

    context("load statement formatter") {

        context("NEW LINE mode") {

            should("format load statement with 1 symbol") {
                val load = LoadStatement(
                    file = StringLiteral("file.bzl"),
                    symbols = listOf(LoadStatement.Symbol(name = StringLiteral("rule_1"), alias = null))
                )

                val builder = StringBuilder()
                formatter.visit(load, position = 1, NEW_LINE, builder)

                val expectedResult = """
                    |load("file.bzl", "rule_1")
                """.trimMargin()

                builder.toString() shouldBe expectedResult
            }

            should("format load statement with 1 aliased symbol") {
                val load = LoadStatement(
                    file = StringLiteral("file.bzl"),
                    symbols = listOf(LoadStatement.Symbol(name = StringLiteral("rule_1"), alias = "alias_1"))
                )

                val builder = StringBuilder()
                formatter.visit(load, position = 1, NEW_LINE, builder)

                val expectedResult = """
                    |load("file.bzl", alias_1 = "rule_1")
                """.trimMargin()

                builder.toString() shouldBe expectedResult
            }

            should("format load statement") {
                val load = LoadStatement(
                    file = StringLiteral("file.bzl"),
                    symbols = listOf(
                        LoadStatement.Symbol(name = StringLiteral("rule_1"), alias = null),
                        LoadStatement.Symbol(name = StringLiteral("rule_2"), alias = null)
                    )
                )

                val builder = StringBuilder()
                formatter.visit(load, position = 1, NEW_LINE, builder)

                val expectedResult = """
                    |load(
                    |${___4}"file.bzl",
                    |${___4}"rule_1",
                    |${___4}"rule_2",
                    |)
                """.trimMargin()

                builder.toString() shouldBe expectedResult
            }

            should("format load statement aliased symbols") {
                val load = LoadStatement(
                    file = StringLiteral("file.bzl"),
                    symbols = listOf(
                        LoadStatement.Symbol(name = StringLiteral("rule_1"), alias = "alias_1"),
                        LoadStatement.Symbol(name = StringLiteral("rule_2"), alias = null),
                    )
                )

                val builder = StringBuilder()
                formatter.visit(load, position = 1, NEW_LINE, builder)

                val expectedResult = """
                    |load(
                    |${___4}"file.bzl",
                    |${___4}alias_1 = "rule_1",
                    |${___4}"rule_2",
                    |)
                """.trimMargin()

                builder.toString() shouldBe expectedResult
            }
        }

        context("CONTINUE LINE mode") {

            should("format load statement with 1 symbol") {
                val load = LoadStatement(
                    file = StringLiteral("file.bzl"),
                    symbols = listOf(LoadStatement.Symbol(name = StringLiteral("rule_1"), alias = null))
                )

                val builder = StringBuilder()
                formatter.visit(load, position = 1, CONTINUE_LINE, builder)

                val expectedResult = """
                    |load("file.bzl", "rule_1")
                """.trimMargin()

                builder.toString() shouldBe expectedResult
            }

            should("format load statement aliased symbols") {
                val load = LoadStatement(
                    file = StringLiteral("file.bzl"),
                    symbols = listOf(
                        LoadStatement.Symbol(name = StringLiteral("rule_1"), alias = "alias_1"),
                        LoadStatement.Symbol(name = StringLiteral("rule_2"), alias = null),
                    )
                )

                val builder = StringBuilder()
                formatter.visit(load, position = 1, CONTINUE_LINE, builder)

                val expectedResult = """
                    |load(
                    |${___4}"file.bzl",
                    |${___4}alias_1 = "rule_1",
                    |${___4}"rule_2",
                    |)
                """.trimMargin()

                builder.toString() shouldBe expectedResult
            }
        }
    }
})