package io.morfly.airin

import io.morfly.airin.label.MavenCoordinates
import io.morfly.pendant.starlark.lang.append

abstract class PackageComponent<P : PackageDescriptor> : Component<P>(), PropertiesHolder,
    ComponentsHolder<P> {

    final override val subcomponents = linkedMapOf<ComponentId, Component<P>>()

    // TODO remove includeSubcomponents
    @InternalAirinApi
    open fun invoke(packageDescriptor: P, includeSubcomponents: Boolean = true): PackageContext {
        val context = PackageContext()
        if (packageDescriptor.skipped) return context

        if (!includeSubcomponents) {
            context.onInvoke(packageDescriptor)
            return context
        }

        val features = subcomponents.values.asSequence()
            .filterIsInstance<FeatureComponent<P>>()
            .filter { it.id in packageDescriptor.featureComponentIds }
            .onEach { it.sharedProperties = sharedProperties }
            .map { it.invoke(packageDescriptor) }
            .toList()

        packageDescriptor.dependencies = packageDescriptor.transformDependencies(features)
        allMavenArtifacts += packageDescriptor.dependencies.values.flatten()
            .filterIsInstance<MavenCoordinates>()

        context.onInvoke(packageDescriptor)

        for (file in context.starlarkFiles.values.flatten()) {
            for (feature in features) {
                file.modifiers.append(feature.modifiers)
            }
        }

        return context
    }

    abstract fun PackageContext.onInvoke(packageDescriptor: P)
}
