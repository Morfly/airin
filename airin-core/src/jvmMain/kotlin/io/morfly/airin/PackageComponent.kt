package io.morfly.airin

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

        context.onInvoke(packageDescriptor)

        for (file in context.starlarkFiles.values.flatten()) {
            for (feature in features) {
                file.modifiers += feature.modifiers
            }
        }

        return context
    }

    abstract fun PackageContext.onInvoke(packageDescriptor: P)
}
