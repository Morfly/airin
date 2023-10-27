package io.morfly.airin

import io.morfly.airin.dsl.FeatureComponentsHolder
import io.morfly.airin.dsl.PackageComponentProperties
import org.gradle.api.Project
import java.io.Serializable

abstract class GradlePackageComponent : PackageComponent<GradleProject>(),
    Serializable,
    FeatureComponentsHolder,
    PackageComponentProperties {

    override var ignored by property(default = false)
    override var shared by property(default = false)
    override var priority by property(default = 1)

    override val id: String = javaClass.simpleName

    open fun canProcess(target: Project): Boolean = false
}
