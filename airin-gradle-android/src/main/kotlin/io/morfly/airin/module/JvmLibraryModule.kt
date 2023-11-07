package io.morfly.airin.module

import io.morfly.airin.GradlePackageComponent
import io.morfly.airin.GradleModule
import io.morfly.airin.PackageContext
import org.gradle.api.Project

abstract class JvmLibraryModule : GradlePackageComponent() {

    override fun canProcess(target: Project): Boolean {
        return false
    }

    override fun PackageContext.onInvoke(packageDescriptor: GradleModule) {

    }
}
