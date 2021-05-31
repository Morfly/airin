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

package org.morfly.airin.starlark.lang.feature

import org.morfly.airin.starlark.elements.EmptyLineStatement
import org.morfly.airin.starlark.elements.Statement
import io.kotest.core.spec.style.FeatureSpec
import io.kotest.matchers.shouldBe


class EmptyLinesFeatureTests : FeatureSpec({

    feature("empty lines feature") {

        scenario("single empty line statement") {
            EmptyLinesFeatureUnderTest().apply {
                // given
                space

                // assertions
                statements.size shouldBe 1
                statements.first() shouldBe EmptyLineStatement
            }
        }

        scenario("multiple new line statements in a row") {
            EmptyLinesFeatureUnderTest().apply {
                // given
                space
                space

                // assertions
                statements.size shouldBe 2
                statements.first() shouldBe EmptyLineStatement
                statements[1] shouldBe EmptyLineStatement
            }
        }
    }
})


private class EmptyLinesFeatureUnderTest : EmptyLinesFeature {
    override val statements = mutableListOf<Statement>()
}