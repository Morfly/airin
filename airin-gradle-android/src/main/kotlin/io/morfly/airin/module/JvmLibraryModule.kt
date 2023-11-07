package io.morfly.airin.module

import io.morfly.airin.ModuleComponent
import io.morfly.airin.GradleModule
import io.morfly.airin.PackageContext
import org.gradle.api.Project

abstract class JvmLibraryModule : ModuleComponent() {

    override fun canProcess(target: Project): Boolean {
        return false
    }

    override fun PackageContext.onInvoke(packageDescriptor: GradleModule) {

    }
}
