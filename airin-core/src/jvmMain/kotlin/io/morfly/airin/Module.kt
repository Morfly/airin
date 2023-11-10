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

    fun append(dependencies: Map<ConfigurationName, Set<Label>>) {
        for ((config, labels) in dependencies)
            transformedDependencies.getOrPut(config, ::mutableSetOf) += labels
    }

    DependencyOverrideProcessor(features, processedDependencies)
        .invoke(this)
        .also(::append)
    ConfigurationOverrideProcessor(features, processedDependencies)
        .invoke(this)
        .also(::append)
    AddedDependencyProcessor(features)
        .invoke(this)
        .also(::append)

    return transformedDependencies.mapValues { (_, dependencies) ->
        dependencies.sortedBy(Label::toString)
    }
}
