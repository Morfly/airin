package io.morfly.airin

import io.morfly.airin.label.MavenCoordinates
import io.morfly.pendant.starlark.lang.append

abstract class AbstractModuleComponent<M : Module> : Component<M>(), PropertiesHolder,
    ComponentsHolder<M> {

    final override val subcomponents = linkedMapOf<ComponentId, Component<M>>()

    @InternalAirinApi
    open fun invoke(module: M): PackageContext {
        val context = PackageContext()
        if (module.skipped) return context

        val features = subcomponents.values.asSequence()
            .filterIsInstance<AbstractFeatureComponent<M>>()
            .filter { it.id in module.featureComponentIds }
            .onEach { it.sharedProperties = sharedProperties }
            .map { it.invoke(module) }
            .toList()

        module.dependencies = module.transformDependencies(features)
        allMavenArtifacts += module.dependencies.values.flatten()
            .filterIsInstance<MavenCoordinates>()

        context.onInvoke(module)

        for (file in context.starlarkFiles.values.flatten()) {
            for (feature in features) {
                file.modifiers.append(feature.modifiers)
            }
        }

        return context
    }

    abstract fun PackageContext.onInvoke(module: M)
}
