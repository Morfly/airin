package com.morfly.airin.starlark.lang.feature

import com.morlfy.airin.starlark.elements.EmptyLineStatement
import com.morlfy.airin.starlark.elements.Statement
import com.morlfy.airin.starlark.lang.feature.EmptyLinesFeature
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