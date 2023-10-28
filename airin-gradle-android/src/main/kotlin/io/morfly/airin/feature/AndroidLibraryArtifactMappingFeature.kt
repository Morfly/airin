package io.morfly.airin.feature

import io.morfly.airin.FeatureContext
import io.morfly.airin.GradleFeatureComponent
import io.morfly.airin.GradleProject
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
    }
}