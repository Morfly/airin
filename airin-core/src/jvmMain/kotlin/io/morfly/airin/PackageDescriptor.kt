package io.morfly.airin

import io.morfly.airin.label.Label

interface PackageDescriptor : PropertiesHolder {

    val name: String

    val isRoot: Boolean

    val label: Label

    val dirPath: String

    val ignored: Boolean

    val packageComponentId: String?

    val featureComponentIds: Set<String>

    val originalDependencies: Map<ConfigurationName, List<Label>>

    var dependencies: Map<ConfigurationName, Set<Label>>

    val subpackages: List<PackageDescriptor>
}
