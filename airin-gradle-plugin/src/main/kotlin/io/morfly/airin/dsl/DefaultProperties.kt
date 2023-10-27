package io.morfly.airin.dsl

import io.morfly.airin.ComponentConflictResolution
import io.morfly.airin.MissingComponentResolution

interface AirinProperties {
    var allowedProjects: MutableSet<String>
    var ignoredProjects: MutableSet<String>
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
