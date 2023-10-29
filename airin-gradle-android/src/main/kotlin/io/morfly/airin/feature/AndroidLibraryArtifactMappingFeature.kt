package io.morfly.airin.feature

import io.morfly.airin.FeatureContext
import io.morfly.airin.GradleFeatureComponent
import io.morfly.airin.GradleProject
import io.morfly.airin.label.MavenCoordinates
import org.gradle.api.Project

abstract class AndroidLibraryArtifactMappingFeature : GradleFeatureComponent() {

    override fun canProcess(target: Project): Boolean = true

    override fun FeatureContext.onInvoke(packageDescriptor: GradleProject) {
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

        onDependency(MavenCoordinates("org.jetbrains.kotlin", "kotlin-bom")) {
            ignore()
        }

        onDependency(MavenCoordinates("androidx.compose", "compose-bom")) {
            ignore()
        }
    }
}