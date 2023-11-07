package io.morfly.airin

import io.morfly.airin.label.Label

interface DependencyProcessor {

    fun invoke(module: Module): Map<ConfigurationName, Set<Label>>
}

class DependencyOverrideProcessor(
    private val features: List<FeatureContext>,
    private val processedDependencies: MutableMap<ConfigurationName, MutableSet<Label>>
) : DependencyProcessor {

    override fun invoke(module: Module): Map<ConfigurationName, Set<Label>> {
        val transformedDependencies = mutableMapOf<ConfigurationName, MutableSet<Label>>()

        for (feature in features) {
            for ((configuration, labels) in module.originalDependencies) {
                for (dependency in labels) {
                    val overrides =
                        feature.dependencyOverrides[dependency.asShortLabel().toString()]
                    val depOverrides: List<DependencyOverride>? =
                        overrides?.get(configuration) ?: overrides?.get(null)
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
        return transformedDependencies
    }
}

class ConfigurationOverrideProcessor(
    private val features: List<FeatureContext>,
    private val processedDependencies: MutableMap<ConfigurationName, MutableSet<Label>>
) : DependencyProcessor {

    override fun invoke(module: Module): Map<ConfigurationName, Set<Label>> {
        val transformedDependencies = mutableMapOf<ConfigurationName, MutableSet<Label>>()

        for (feature in features) {
            for ((configuration, labels) in module.originalDependencies) {
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
        return transformedDependencies
    }
}

class AddedDependencyProcessor(private val features: List<FeatureContext>) : DependencyProcessor {

    override fun invoke(module: Module): Map<ConfigurationName, Set<Label>> {
        val transformedDependencies = mutableMapOf<ConfigurationName, MutableSet<Label>>()

        for (feature in features) {
            for ((configuration, dependencies) in feature.addedDependencies) {
                for (dependency in dependencies) {
                    transformedDependencies.getOrPut(configuration, ::mutableSetOf) += dependency
                }
            }
        }
        return transformedDependencies
    }
}