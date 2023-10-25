package io.morfly.airin

import io.morfly.airin.label.Label

interface PackageDescriptor : PropertiesHolder {

    val name: String

    val isRoot: Boolean

    val label: Label

    val path: String

    val ignored: Boolean

    val packageComponentId: String?

    val featureComponentIds: Set<String>

    val dependencies: Map<ConfigurationName, List<Label>>

    val subpackages: List<PackageDescriptor>
}
