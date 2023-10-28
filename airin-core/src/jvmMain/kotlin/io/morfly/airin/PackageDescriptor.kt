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

    val dependencies: Map<ConfigurationName, Set<Label>>

    val subpackages: List<PackageDescriptor>

    fun applyDependencies(dependencies: Map<ConfigurationName, Set<Label>>)
}

fun PackageDescriptor.transformDependencies(features: List<FeatureContext>): Map<ConfigurationName, Set<Label>> {
    val transformedDependencies = mutableMapOf<ConfigurationName, MutableSet<Label>>()

    for (feature in features) {
        for ((configuration, labels) in this.originalDependencies) {
            for (dependency in labels) {
                val depOverride = feature.dependencyOverrides[dependency.toString()]?.get(configuration)
                val configOverride = feature.configurationOverrides[configuration]

                val transformedConfig = depOverride?.configuration ?: configOverride?.configuration ?: continue
                val transformedDep = depOverride?.label ?: dependency

                transformedDependencies.getOrPut(transformedConfig, ::mutableSetOf) += transformedDep
            }
        }
    }
    return transformedDependencies
}
