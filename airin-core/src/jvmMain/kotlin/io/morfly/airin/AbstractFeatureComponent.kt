package io.morfly.airin

abstract class AbstractFeatureComponent<M : Module> : Component<M>() {

    @InternalAirinApi
    open fun invoke(
        module: M,
        sharedProperties: MutableMap<String, Any>
    ): FeatureContext {
        val context = FeatureContext(sharedProperties)
        context.onInvoke(module)
        return context
    }

    abstract fun FeatureContext.onInvoke(module: M)
}
