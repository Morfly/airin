package io.morfly.airin

import io.morfly.airin.label.Label
import io.morfly.pendant.starlark.lang.append

abstract class PackageComponent<P : PackageDescriptor> : Component<P>(), PropertiesHolder,
    ComponentsHolder<P> {

    final override val subcomponents = mutableMapOf<ComponentId, Component<P>>()

    @InternalAirinApi
    open fun invoke(packageDescriptor: P, includeSubcomponents: Boolean = true): PackageContext {
        val context = PackageContext()
        if (packageDescriptor.ignored) return context

        if (!includeSubcomponents) {
            context.onInvoke(packageDescriptor)
            return context
        }

        val features = subcomponents.values.asSequence()
            .filterIsInstance<FeatureComponent<P>>()
            .filter { it.id in packageDescriptor.featureComponentIds }
            .map { it.invoke(packageDescriptor) }
            .toList()

        packageDescriptor.applyDependencies(packageDescriptor.transformDependencies(features))
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
}
