package io.morfly.airin.dsl

import io.morfly.airin.ComponentsHolder
import io.morfly.airin.GradleFeatureComponent
import io.morfly.airin.GradleProject
import io.morfly.airin.HasId
import org.gradle.api.Action
import org.gradle.api.model.ObjectFactory
import javax.inject.Inject

interface FeatureComponentsHolder :
    HasId,
    ComponentsHolder<GradleProject> {

    @get:Inject
    val objects: ObjectFactory

    fun <B : GradleFeatureComponent> include(
        type: Class<B>,
        config: Action<B>? = null
    ) {
        val component = objects.newInstance(type)
        component.parentId = id
        if (component.id in subcomponents) {
            error("Duplicate component $type found in ${this.javaClass}!")
        }
        config?.execute(component)
        subcomponents[component.id] = component
    }
}
