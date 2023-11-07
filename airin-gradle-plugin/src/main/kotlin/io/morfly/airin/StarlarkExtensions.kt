package io.morfly.airin

import io.morfly.airin.label.MavenCoordinates
import io.morfly.pendant.starlark.artifact
import io.morfly.pendant.starlark.lang.context.FunctionCallContext

fun FunctionCallContext.applyDependenciesFrom(packageDescriptor: GradleModule) {
    for ((configuration, dependencies) in packageDescriptor.dependencies) {
        configuration `=` dependencies.mapNotNull {
            when (it) {
                is MavenCoordinates -> artifact(it.asComparable().toString())
                else -> it.asBazelLabel()?.toString()
            }
        }
    }
}
