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

    val processedDependencies = mutableMapOf<ConfigurationName, MutableSet<Label>>()

    for (feature in features) {
        for ((configuration, labels) in this.originalDependencies) {
            for (dependency in labels) {
                if (name == "app")
                    println("TTAGG dep: ${dependency}")

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

    for (feature in features) {
        for ((configuration, labels) in this.originalDependencies) {
            for (dependency in labels) {
                if (dependency in processedDependencies[configuration].orEmpty()) continue

                val configOverride = feature.configurationOverrides[configuration]
                val config = configOverride?.configuration
                    ?: continue
                val dep = dependency.asComparable()
                transformedDependencies.getOrPut(config, ::mutableSetOf) += dep
            }
        }
    }
    return transformedDependencies
}
