package io.morfly.airin.feature

import io.morfly.airin.FeatureContext
import io.morfly.airin.GradleFeatureComponent
import io.morfly.airin.GradleProject
import org.gradle.api.Project

abstract class DaggerAndroidFeature : GradleFeatureComponent() {

    override fun canProcess(target: Project): Boolean {
        return false
    }

    override fun FeatureContext.onInvoke(packageDescriptor: GradleProject) {

    }
}
