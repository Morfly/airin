package io.morfly.airin

import io.morfly.airin.label.MavenCoordinates
import io.morfly.pendant.starlark.artifact
import io.morfly.pendant.starlark.lang.context.FunctionCallContext

fun FunctionCallContext.applyDependenciesFrom(module: GradleModule) {
    for ((configuration, dependencies) in module.dependencies) {
        configuration `=` dependencies.mapNotNull {
            when (it) {
                is MavenCoordinates -> artifact(it.asShortLabel().toString())
                else -> it.asBazelLabel()?.toString()
            }
        }
    }
}
