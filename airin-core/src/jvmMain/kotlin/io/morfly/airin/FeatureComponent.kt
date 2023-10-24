package io.morfly.airin

abstract class FeatureComponent<P : PackageDescriptor> : Component<P>(), PropertiesHolder {

    @InternalAirinApi
    open fun invoke(packageDescriptor: P): FeatureContext {
        val context = FeatureContext()
        context.onInvoke(packageDescriptor)
        return context
    }

    abstract fun FeatureContext.onInvoke(packageDescriptor: P)
}
