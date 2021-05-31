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

package org.morfly.airin.starlark.format

import org.morfly.airin.starlark.elements.EmptyLineStatement
import org.morfly.airin.starlark.elements.PositionMode.*
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