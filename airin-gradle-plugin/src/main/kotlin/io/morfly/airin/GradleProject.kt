package io.morfly.airin

import io.morfly.airin.label.GradleLabel
import io.morfly.airin.label.Label
import java.io.Serializable

data class GradleProject(
    override val name: String,
    override val label: GradleLabel,
    override val dirPath: String,
    override val isRoot: Boolean,
    override val ignored: Boolean,
    override val packageComponentId: String?,
    override val featureComponentIds: Set<String>
) : PackageDescriptor, Serializable {

    override lateinit var originalDependencies: Map<ConfigurationName, List<Label>>
        internal set

    override lateinit var dependencies: Map<ConfigurationName, Set<Label>>
        internal set
    override lateinit var subpackages: List<GradleProject>
        internal set

    override val properties = mutableMapOf<String, Any?>()

    override fun applyDependencies(dependencies: Map<ConfigurationName, Set<Label>>) {
        this.dependencies = dependencies
    }
}
