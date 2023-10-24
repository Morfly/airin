package io.morfly.airin

import io.morfly.airin.dsl.FeatureComponentProperties
import org.gradle.api.Project
import java.io.Serializable

abstract class GradleFeatureComponent : FeatureComponent<GradleProject>(),
    Serializable,
    FeatureComponentProperties {

    override val shared: Boolean by property(default = false)
    override var ignored: Boolean by property(default = false)

    internal lateinit var parentId: String

    override val id: String by lazy { "$parentId/${javaClass.simpleName}" }

    abstract fun canProcess(target: Project): Boolean
}
