package io.morfly.airin.dsl

import io.morfly.airin.ComponentConflictResolution
import io.morfly.airin.ConfigurationName
import io.morfly.airin.GradleProjectDecorator
import io.morfly.airin.MissingComponentResolution

interface AirinProperties {
    var enabled: Boolean
    var projectDecorator: Class<out GradleProjectDecorator>?
    var targets: MutableSet<String>
    var skippedProjects: MutableSet<String>
    var configurations: MutableSet<ConfigurationName>
    var skippedConfigurations: MutableSet<ConfigurationName>
    var onComponentConflict: ComponentConflictResolution
    var onMissingComponent: MissingComponentResolution
}

interface PackageComponentProperties {
    var ignored: Boolean
    var shared: Boolean
    var priority: Int
}

interface FeatureComponentProperties {
    var ignored: Boolean
    var shared: Boolean
}

val defaultConfigurations
    get() = mutableSetOf(
        "implementation",
        "debugImplementation",
        "releaseImplementation",
        "api",
        "debugApi",
        "releaseApi",
        "kapt",
        "ksp"
    )
