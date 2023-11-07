package io.morfly.airin.dsl

import io.morfly.airin.ComponentsHolder
import io.morfly.airin.ModuleComponent
import io.morfly.airin.GradleModule
import org.gradle.api.Action
import org.gradle.api.model.ObjectFactory
import javax.inject.Inject

interface PackageComponentsHolder : ComponentsHolder<GradleModule> {

    @get:Inject
    val objects: ObjectFactory

    fun <B : ModuleComponent> register(
        type: Class<B>,
        config: Action<B>? = null
    ) {
        val component = objects.newInstance(type)
        if (component.id in subcomponents) {
            error("Duplicate component $type found in ${this.javaClass}!")
        }
        config?.execute(component)
        subcomponents[component.id] = component
    }
}
