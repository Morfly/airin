package io.morfly.airin

abstract class AbstractFeatureComponent<M : Module> : Component<M>(), PropertiesHolder {

    @InternalAirinApi
    open fun invoke(packageDescriptor: M): FeatureContext {
        val context = FeatureContext()
        context.onInvoke(packageDescriptor)
        return context
    }

    abstract fun FeatureContext.onInvoke(packageDescriptor: M)
}
