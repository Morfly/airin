package io.morfly.airin

import io.morfly.airin.label.Label
import io.morfly.pendant.starlark.lang.append

abstract class PackageComponent<P : PackageDescriptor> : Component<P>(), PropertiesHolder,
    ComponentsHolder<P> {

    final override val subcomponents = mutableListOf<Component<P>>()

    @InternalAirinApi
    open fun invoke(packageDescriptor: P, invokeSubcomponents: Boolean = true): PackageContext {
        val context = PackageContext()

        if (!invokeSubcomponents) {
            context.onInvoke(packageDescriptor)
            return context
        }

        val features = subcomponents.asSequence()
            .filterIsInstance<FeatureComponent<P>>()
            .filter { it.id in packageDescriptor.featureComponentIds }
            .map { it.invoke(packageDescriptor) }
            .onEach { } // TODO filter ignored module
            .toList()

        packageDescriptor.transformDependencies(features)
        context.onInvoke(packageDescriptor)

        for (file in context.starlarkFiles.values.flatten()) {
            for (feature in features) {
                file.modifiers.append(feature.modifiers)
            }
        }

        return context
    }

    abstract fun PackageContext.onInvoke(packageDescriptor: P)

    private fun P.transformDependencies(features: List<FeatureContext>): Map<ConfigurationName, Set<Label>> {
        val transformedDependencies = mutableMapOf<ConfigurationName, MutableSet<Label>>()

        for (feature in features) {
            for ((configuration, labels) in dependencies) {
                for (dependency in labels) {
                    val depOverride = feature.dependencyOverrides[dependency.toString()]?.get(configuration)
                    val configOverride = feature.configurationOverrides[configuration]

                    val transformedConfig = depOverride?.configuration ?: configOverride?.configuration ?: configuration
                    val transformedDep = depOverride?.label ?: dependency

                    transformedDependencies.getOrPut(transformedConfig, ::mutableSetOf) += transformedDep
                }
            }
        }
        return transformedDependencies
    }
}
