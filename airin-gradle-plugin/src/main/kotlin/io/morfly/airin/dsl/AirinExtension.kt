package io.morfly.airin.dsl

import io.morfly.airin.Component
import io.morfly.airin.ComponentConflictResolution.UsePriority
import io.morfly.airin.ComponentId
import io.morfly.airin.GradleProject
import io.morfly.airin.HasId
import io.morfly.airin.MissingComponentResolution.Ignore
import io.morfly.airin.PropertiesHolder
import io.morfly.airin.property

abstract class AirinExtension :
    HasId,
    PackageComponentsHolder,
    FeatureComponentsHolder,
    PropertiesHolder,
    AirinProperties {

    override val id: String = "Airin"

    override val subcomponents: MutableMap<ComponentId, Component<GradleProject>> = mutableMapOf()

    override val properties: MutableMap<String, Any?> = mutableMapOf()

    override var allowedConfigurations by property(
        mutableSetOf("implementation", "api", "kapt", "ksp")
    )
    override var ignoredConfigurations by property(mutableSetOf<String>())
    override var onComponentConflict by property(UsePriority)
    override var onMissingComponent by property(Ignore)

    companion object {
        const val NAME = "airin"
    }
}
