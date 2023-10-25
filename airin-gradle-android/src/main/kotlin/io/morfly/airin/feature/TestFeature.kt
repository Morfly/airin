package io.morfly.airin.feature

import io.morfly.airin.FeatureContext
import io.morfly.airin.GradleFeatureComponent
import io.morfly.airin.GradleProject
import io.morfly.airin.label.BazelLabel
import org.gradle.api.Project

abstract class TestFeature : GradleFeatureComponent() {

    override fun canProcess(target: Project): Boolean {
        return true
    }

    override fun FeatureContext.onInvoke(packageDescriptor: GradleProject) {
        onConfiguration("implementation") {
            overrideWith("deps")
        }

        onDependency("org") {
            overrideWith(BazelLabel(""))
        }
    }
}