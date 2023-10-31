package io.morfly.airin.dsl

import io.morfly.airin.ComponentConflictResolution
import io.morfly.airin.ConfigurationName
import io.morfly.airin.GradleProjectDecorator
import io.morfly.airin.MissingComponentResolution

interface AirinProperties {
    var projectDecorator: Class<out GradleProjectDecorator>
    var allowedProjects: MutableSet<String>
    var ignoredProjects: MutableSet<String>
    var allowedConfigurations: MutableSet<ConfigurationName>
    var ignoredConfigurations: MutableSet<ConfigurationName>
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
