package io.morfly.airin

import io.morfly.airin.label.MavenCoordinates
import io.morfly.pendant.starlark.lang.append
import io.morfly.pendant.starlark.lang.context.FileContext

abstract class AbstractModuleComponent<M : Module> : Component<M>(), ComponentsHolder<M> {

    final override val subcomponents = linkedMapOf<ComponentId, Component<M>>()

    @InternalAirinApi
    open fun invoke(
        module: M,
        sharedProperties: MutableMap<String, Any>
    ): ModuleContext {
        val context = ModuleContext(sharedProperties)
        if (module.skipped) return context

        val features = subcomponents.values.asSequence()
            .filterIsInstance<AbstractFeatureComponent<M>>()
            .filter { it.id in module.featureComponentIds }
            .map { it.invoke(module, context.sharedProperties) }
            .toList()

        module.dependencies = module.transformDependencies(features)
        context.allMavenArtifacts += module.dependencies.values.flatten()
            .filterIsInstance<MavenCoordinates>()

        context.onInvoke(module)

        for (file in context.starlarkFiles.values.flatten()) {
            for (feature in features) {
                file.modifiers.append(feature.modifiers)
            }
        }

        return context
    }

    abstract fun ModuleContext.onInvoke(module: M)
}

fun <M : Module> AbstractModuleComponent<M>.extractFilePaths(module: M): Map<String, List<FileContext>> {
    val context = ModuleContext()
    context.onInvoke(module)
    return context.starlarkFiles
}
