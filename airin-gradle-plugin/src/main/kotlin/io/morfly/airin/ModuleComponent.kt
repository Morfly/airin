package io.morfly.airin

import io.morfly.airin.dsl.FeatureComponentsHolder
import io.morfly.airin.dsl.PackageComponentProperties
import org.gradle.api.Project
import java.io.Serializable

abstract class ModuleComponent : AbstractModuleComponent<GradleModule>(),
    Serializable,
    FeatureComponentsHolder,
    PackageComponentProperties {

    // TODO filter out ignored components
    final override var ignored by property(default = false)
    final override var shared by property(default = false)
    final override var priority by property(default = 1)

    override val id: String = javaClass.simpleName

    open fun canProcess(target: Project): Boolean = false
}
