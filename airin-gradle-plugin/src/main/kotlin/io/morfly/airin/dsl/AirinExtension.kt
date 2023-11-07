package io.morfly.airin.dsl

import io.morfly.airin.Component
import io.morfly.airin.ComponentConflictResolution.UsePriority
import io.morfly.airin.ComponentId
import io.morfly.airin.GradleProject
import io.morfly.airin.GradleProjectDecorator
import io.morfly.airin.HasId
import io.morfly.airin.MissingComponentResolution.Ignore
import io.morfly.airin.PropertiesHolder
import io.morfly.airin.property
import org.gradle.api.Project

abstract class AirinExtension :
    HasId,
    PackageComponentsHolder,
    FeatureComponentsHolder,
    PropertiesHolder,
    AirinProperties {

    override val id: String = "Airin"

    override val subcomponents: MutableMap<ComponentId, Component<GradleProject>> = linkedMapOf()

    override val properties: MutableMap<String, Any?> = mutableMapOf()

    override var enabled by property(true)
    override var projectDecorator: Class<out GradleProjectDecorator> by property(
        GradleProjectDecorator::class.java
    )
    override var inputProjects by property(mutableSetOf<String>())
    override var skippedProjects by property(mutableSetOf<String>())
    override var configurations by property(
        mutableSetOf("implementation", "api", "kapt", "ksp")
    )
    override var skippedConfigurations by property(mutableSetOf<String>())
    override var onComponentConflict by property(UsePriority)
    override var onMissingComponent by property(Ignore)

    // TODO update
    fun migrate(project: Project) {
        inputProjects += project.path
    }

    companion object {
        const val NAME = "airin"
    }
}
