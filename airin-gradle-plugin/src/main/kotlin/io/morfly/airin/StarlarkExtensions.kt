package io.morfly.airin

import io.morfly.airin.label.BazelLabel
import io.morfly.airin.label.MavenCoordinates
import io.morfly.pendant.starlark.lang.context.FunctionCallContext

fun FunctionCallContext.applyDependenciesFrom(packageDescriptor: GradleProject) {
    for ((configuration, dependencies) in packageDescriptor.dependencies) {
        configuration `=` dependencies.mapNotNull {
            when (it) {
                is MavenCoordinates -> it.copy(version = null).toString()
                is BazelLabel -> it.toString()
                else -> null
            }
        }
    }
}
