package io.morfly.airin

import io.morfly.airin.label.MavenCoordinates
import io.morfly.pendant.starlark.artifact
import io.morfly.pendant.starlark.lang.context.FunctionCallContext

fun FunctionCallContext.applyDependenciesFrom(packageDescriptor: GradleProject) {
    for ((configuration, dependencies) in packageDescriptor.dependencies) {
        configuration `=` dependencies.mapNotNull {
            when (it) {
                is MavenCoordinates -> artifact(it.copy(version = null).toString())
                else -> it.asBazelLabel()?.toString()
            }
        }
    }
}
