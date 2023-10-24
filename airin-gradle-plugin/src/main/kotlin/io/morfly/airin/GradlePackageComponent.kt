package io.morfly.airin

import io.morfly.airin.dsl.FeatureComponentsHolder
import io.morfly.airin.dsl.PackageComponentProperties
import org.gradle.api.Project
import java.io.Serializable

abstract class GradlePackageComponent : PackageComponent<GradleProject>(),
    Serializable,
    FeatureComponentsHolder,
    PackageComponentProperties {

    override var shared: Boolean by property(default = false)
    override var ignored: Boolean by property(default = false)
    override var priority: Int by property(default = 1)

    override val id: String = javaClass.simpleName

    open fun canProcess(target: Project): Boolean = false
}
