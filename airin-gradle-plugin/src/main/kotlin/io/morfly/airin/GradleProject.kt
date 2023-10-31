package io.morfly.airin

import io.morfly.airin.label.GradleLabel
import io.morfly.airin.label.Label
import java.io.Serializable

data class GradleProject(
    override val name: String,
    override val label: GradleLabel,
    override val dirPath: String,
    override val isRoot: Boolean,
) : PackageDescriptor(), Serializable {

    override val ignored: Boolean
        get() = packageComponentId == null

    override var packageComponentId: String? = null
        internal set

    override lateinit var featureComponentIds: Set<String>
        internal set

    override lateinit var originalDependencies: Map<ConfigurationName, List<Label>>
        internal set

    override lateinit var dependencies: Map<ConfigurationName, List<Label>>

    override lateinit var subpackages: List<GradleProject>
        internal set

    override val properties = mutableMapOf<String, Any?>()
}
