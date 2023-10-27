package io.morfly.airin.dsl

import io.morfly.airin.ComponentsHolder
import io.morfly.airin.GradlePackageComponent
import io.morfly.airin.GradleProject
import org.gradle.api.Action
import org.gradle.api.model.ObjectFactory
import javax.inject.Inject

interface PackageComponentsHolder : ComponentsHolder<GradleProject> {

    @get:Inject
    val objects: ObjectFactory

    fun <B : GradlePackageComponent> register(
        type: Class<B>,
        config: Action<B>? = null
    ) {
        val component = objects.newInstance(type)
        config?.execute(component)
        subcomponents[component.id] = component
    }
}
