package io.morfly.airin

import io.morfly.airin.label.GradleLabel
import io.morfly.airin.label.Label
import java.io.Serializable

data class GradleModule(
    override val name: String,
    override val label: GradleLabel,
    override val dirPath: String,
    override val relativeDirPath: String,
    override val isRoot: Boolean,
    override val moduleComponentId: String?,
    override val featureComponentIds: Set<String>,
    override val originalDependencies: Map<ConfigurationName, List<Label>>
) : Module(), Serializable {

    override val skipped: Boolean
        get() = moduleComponentId == null

    override lateinit var dependencies: Map<ConfigurationName, List<Label>>

    override val properties = mutableMapOf<String, Any?>()
}