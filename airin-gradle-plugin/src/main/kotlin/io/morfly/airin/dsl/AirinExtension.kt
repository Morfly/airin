package io.morfly.airin.dsl

import io.morfly.airin.Component
import io.morfly.airin.ComponentConflictResolution.UsePriority
import io.morfly.airin.ComponentId
import io.morfly.airin.GradleModule
import io.morfly.airin.GradleProjectDecorator
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

    override val subcomponents: MutableMap<ComponentId, Component<GradleModule>> = linkedMapOf()

    override val properties: MutableMap<String, Any> = mutableMapOf()

    override var enabled by property(default = true)
    override var projectDecorator by property<Class<out GradleProjectDecorator>?>(default = null)
    override var targets by property(default = mutableSetOf<String>())
    override var skippedProjects by property(default = mutableSetOf<String>())
    override var configurations by property(default = defaultConfigurations)
    override var skippedConfigurations by property(default = mutableSetOf<String>())
    override var onComponentConflict by property(default = UsePriority)
    override var onMissingComponent by property(default = Ignore)

    companion object {
        const val NAME = "airin"
    }
}
