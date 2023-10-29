package io.morfly.airin

import io.morfly.airin.label.Label

abstract class PackageDescriptor : PropertiesHolder {

    abstract val name: String

    abstract val isRoot: Boolean

    abstract val label: Label

    abstract val dirPath: String

    abstract val ignored: Boolean

    abstract val packageComponentId: String?

    abstract val featureComponentIds: Set<String>

    abstract val originalDependencies: Map<ConfigurationName, List<Label>>

    abstract var dependencies: Map<ConfigurationName, Set<Label>>
        internal set

    abstract val subpackages: List<PackageDescriptor>
}

fun PackageDescriptor.transformDependencies(features: List<FeatureContext>): Map<ConfigurationName, Set<Label>> {
    val transformedDependencies = mutableMapOf<ConfigurationName, MutableSet<Label>>()

    val processedDependencies = mutableMapOf<ConfigurationName, MutableSet<Label>>()

    // Process dependency overrides
    for (feature in features) {
        for ((configuration, labels) in this.originalDependencies) {
            for (dependency in labels) {
                val overrides = feature.dependencyOverrides[dependency.asComparable().toString()]
                val depOverride = overrides?.get(configuration) ?: overrides?.get(null)
                val configOverride = feature.configurationOverrides[configuration]

                if (depOverride is DependencyOverride.Override) {
                    val config = depOverride.configuration
                        ?: configOverride?.configuration
                        ?: continue
                    val dep = depOverride.label
                    transformedDependencies.getOrPut(config, ::mutableSetOf) += dep
                }

                if (depOverride != null) {
                    processedDependencies.getOrPut(configuration, ::mutableSetOf) += dependency
                }
            }
        }
    }

    // Process configuration overrides for the remaining dependencies
    for (feature in features) {
        for ((configuration, labels) in this.originalDependencies) {
            for (dependency in labels) {
                if (dependency in processedDependencies[configuration].orEmpty()) continue

                val configOverride = feature.configurationOverrides[configuration]
                val config = configOverride?.configuration
                    ?: continue
                transformedDependencies.getOrPut(config, ::mutableSetOf) += dependency
            }
        }
    }
    return transformedDependencies
}
