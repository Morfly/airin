/*
 * Copyright 2023 Pavlo Stavytskyi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
