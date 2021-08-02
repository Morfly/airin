package org.morfly.airin

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import org.morfly.airin.BazelLabelFormat.*
import org.morfly.airin.BazelLabelFormat.Short

class MavenArtifactTests : ShouldSpec({
    val artifact = MavenArtifact("org.airin.test:test:0.0.1")
    val artifactWithoutVersion = MavenArtifact("org.airin.test:test")
    val artifactWithoutGroup = MavenArtifact("test:0.0.1")
    val artifactWithNameOnly = MavenArtifact("test")
    val artifactWithMoreComponents = MavenArtifact("org.airin.test:test:0.0.1:jar")

    should("return correct label") {
        artifact.label shouldBe "org.airin.test:test:0.0.1"
        artifactWithoutVersion.label shouldBe "org.airin.test:test"
        artifactWithoutGroup.label shouldBe "test:0.0.1"
        artifactWithNameOnly.label shouldBe "test"
        artifactWithMoreComponents.label shouldBe "org.airin.test:test:0.0.1:jar"
    }

    should("return correct shortLabel") {
        artifact.shortLabel shouldBe "org.airin.test:test"
        artifactWithoutVersion.shortLabel shouldBe "org.airin.test:test"
        artifactWithoutGroup.shortLabel shouldBe "test:0.0.1"
        artifactWithNameOnly.shortLabel shouldBe "test"
        artifactWithMoreComponents.shortLabel shouldBe "org.airin.test:test"
    }

    context("bazelLabel function") {

        should("return label in default format") {
            artifact.bazelLabel() shouldBe "@maven//:org_airin_test_test"
        }

        should("return label with custom repo name") {
            val expectedResult = "@maven_custom//:org_airin_test_test"

            artifact.bazelLabel(format = FullRepo("maven_custom")) shouldBe expectedResult
            artifact.bazelLabel(format = FullRepoOmitsTarget("maven_custom")) shouldBe expectedResult
        }

        should("return same label for different label formats") {
            val expectedResult = "@maven//:org_airin_test_test"

            artifact.bazelLabel(format = Full) shouldBe expectedResult
            artifact.bazelLabel(format = FullOmitsTarget) shouldBe expectedResult
            artifact.bazelLabel(format = ShortColon) shouldBe expectedResult
            artifact.bazelLabel(format = Short) shouldBe expectedResult
        }
    }
})