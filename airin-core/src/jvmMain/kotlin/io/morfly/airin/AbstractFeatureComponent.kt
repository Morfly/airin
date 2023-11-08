package io.morfly.airin

abstract class AbstractFeatureComponent<M : Module> : Component<M>() {

    @InternalAirinApi
    open fun invoke(module: M): FeatureContext {
        val context = FeatureContext()
        context.onInvoke(module)
        return context
    }

    abstract fun FeatureContext.onInvoke(module: M)
}
