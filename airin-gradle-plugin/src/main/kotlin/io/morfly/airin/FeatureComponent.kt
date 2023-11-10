package io.morfly.airin

import io.morfly.airin.dsl.FeatureComponentProperties
import org.gradle.api.Project
import java.io.Serializable

abstract class FeatureComponent : AbstractFeatureComponent<GradleModule>(),
    Serializable,
    FeatureComponentProperties {

    final override var ignored by property(default = false)
    final override var shared by property(default = false)

    internal lateinit var parentId: String

    override val id: String by lazy { "$parentId/${javaClass.simpleName}" }

    abstract fun canProcess(target: Project): Boolean
}
