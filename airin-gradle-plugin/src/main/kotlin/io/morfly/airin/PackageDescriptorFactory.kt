package io.morfly.airin

import org.gradle.api.Project

interface PackageDescriptorFactory<P: GradleProject> {
    fun create(target: Project, component: GradlePackageComponent?): P
}

class PackageDescriptorFactoryImpl: PackageDescriptorFactory<GradleProject> {
    override fun create(target: Project, component: GradlePackageComponent?): GradleProject {
        TODO("Not yet implemented")
    }
}
