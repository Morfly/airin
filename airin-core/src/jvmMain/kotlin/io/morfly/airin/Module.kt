package io.morfly.airin

import io.morfly.airin.label.Label

abstract class Module : PropertiesHolder {

    abstract val name: String

    abstract val isRoot: Boolean

    abstract val label: Label

    abstract val dirPath: String

    abstract val relativeDirPath: String

    abstract val skipped: Boolean

    abstract val moduleComponentId: String?

    abstract val featureComponentIds: Set<String>

    abstract val originalDependencies: Map<ConfigurationName, List<Label>>

    abstract var dependencies: Map<ConfigurationName, List<Label>>
        internal set
}

fun Module.transformDependencies(features: List<FeatureContext>): Map<ConfigurationName, List<Label>> {
    val transformedDependencies = mutableMapOf<ConfigurationName, MutableSet<Label>>()
    val processedDependencies = mutableMapOf<ConfigurationName, MutableSet<Label>>()

    // Process dependency overrides
    for (feature in features) {
        for ((configuration, labels) in this.originalDependencies) {
            for (dependency in labels) {
                val overrides = feature.dependencyOverrides[dependency.asShortLabel().toString()]
                val depOverrides: List<DependencyOverride>? = overrides?.get(configuration) ?: overrides?.get(null)
                val configOverrides = feature.configurationOverrides[configuration]

                for (depOverride in depOverrides.orEmpty()) {
                    val configs = depOverride.configuration?.let(::listOf)
                        ?: configOverrides?.map { it.configuration }
                        ?: continue

                    for (config in configs) {
                        val dep = depOverride.label
                        transformedDependencies.getOrPut(config, ::mutableSetOf) += dep
                    }
                }

                if (depOverrides != null) {
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

                val configOverrides = feature.configurationOverrides[configuration]
                for (override in configOverrides.orEmpty()) {
                    val config = override.configuration
                    transformedDependencies.getOrPut(config, ::mutableSetOf) += dependency
                }
            }
        }
    }

    for (feature in features) {
        for ((configuration, dependencies) in feature.addedDependencies) {
            for (dependency in dependencies) {
                transformedDependencies.getOrPut(configuration, ::mutableSetOf) += dependency
            }
        }
    }

    return transformedDependencies
        .map { (config, deps) -> config to deps.sortedBy { it.toString() } }
        .toMap()
}