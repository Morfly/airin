package io.morfly.airin

import io.morfly.pendant.starlark.artifact
import io.morfly.pendant.starlark.lang.context.FunctionCallContext

fun FunctionCallContext.applyDependenciesFrom(packageDescriptor: GradleProject) {
    for ((configuration, dependencies) in packageDescriptor.dependencies) {
        configuration `=` dependencies.map { artifact(it.toString()) }
    }
}
