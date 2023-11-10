package io.morfly.airin.feature

import io.morfly.airin.FeatureComponent
import io.morfly.airin.FeatureContext
import io.morfly.airin.GradleModule
import io.morfly.airin.label.MavenCoordinates
import org.gradle.api.Project

abstract class ArtifactMappingFeature : FeatureComponent() {

    override fun canProcess(target: Project): Boolean = true

    override fun FeatureContext.onInvoke(module: GradleModule) {
        onConfiguration("implementation") {
            overrideWith("deps")
        }

        onConfiguration("api") {
            overrideWith("exports")
        }

        onConfiguration("kapt") {
            overrideWith("plugins")
        }

        onConfiguration("ksp") {
            overrideWith("plugins")
        }

        onDependency(MavenCoordinates("org.jetbrains.kotlin", "kotlin-bom")) {}

        onDependency(MavenCoordinates("org.jetbrains.kotlin", "kotlin-stdlib-jdk8")) {}

        onDependency(MavenCoordinates("androidx.compose", "compose-bom")) {}
    }
}