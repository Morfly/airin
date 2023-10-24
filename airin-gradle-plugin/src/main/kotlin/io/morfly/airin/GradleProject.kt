package io.morfly.airin

import io.morfly.airin.label.GradleLabel
import io.morfly.airin.label.Label
import java.io.Serializable

// TODO add gradlePath
open class GradleProject(
    override val name: String,
    override val label: GradleLabel,
    override val path: String,
    override val isRoot: Boolean,
    override val ignored: Boolean,
    override val packageComponentId: String?,
    override val featureComponentIds: Set<String>
) : PackageDescriptor, Serializable {

    override lateinit var dependencies: Map<ConfigurationName, List<Label>>
        internal set
    override lateinit var subpackages: List<GradleProject>
        internal set

    override val properties = mutableMapOf<String, Any?>()

    fun dependencies(component: GradlePackageComponent): Map<String, List<Label>> {
        return dependencies
    }
}
