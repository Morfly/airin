package io.morfly.airin

import io.morfly.airin.label.GradleLabel
import io.morfly.airin.label.Label
import java.io.Serializable

data class GradleProject(
    override val name: String,
    override val label: GradleLabel,
    override val dirPath: String,
    override val relativeDirPath: String,
    override val isRoot: Boolean,
    override val packageComponentId: String?,
    override val featureComponentIds: Set<String>,
    override val originalDependencies: Map<ConfigurationName, List<Label>>
) : PackageDescriptor(), Serializable {

    override val skipped: Boolean
        get() = packageComponentId == null

    override lateinit var dependencies: Map<ConfigurationName, List<Label>>

    // TODO() remove
    override lateinit var subpackages: List<GradleProject>
        internal set

    override val properties = mutableMapOf<String, Any?>()
}
