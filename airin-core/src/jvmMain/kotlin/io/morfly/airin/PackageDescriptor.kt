package io.morfly.airin

import io.morfly.airin.label.Label
import io.morfly.airin.label.MavenCoordinates

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
                val overrides = feature.dependencyOverrides[dependency.shorten().toString()]
                val depOverride = overrides?.get(configuration) ?: overrides?.get(null)
                val configOverride = feature.configurationOverrides[configuration]

                val isHilt = if (dependency is MavenCoordinates && this.name == "app") {
                    "hilt" in dependency.name
                } else false

                when (depOverride) {
                    is DependencyOverride.Ignore -> {}
                    is DependencyOverride.Override -> {
                        val config = depOverride.configuration
                            ?: configOverride?.configuration
                            ?: continue
                        val dep = depOverride.label
                        transformedDependencies.getOrPut(config, ::mutableSetOf) += dep

                        if (isHilt) {
                            println("TTAGG override. $config : $dep")
                        }
                    }

                    null -> {
                        val config = configOverride?.configuration
                            ?: continue
                        val dep = dependency.shorten()
                        transformedDependencies.getOrPut(config, ::mutableSetOf) += dep

                        if (isHilt) {
                            println("TTAGG config. $config : $dep")
                        }
                    }
                }
            }
        }
    }
    return transformedDependencies
}
