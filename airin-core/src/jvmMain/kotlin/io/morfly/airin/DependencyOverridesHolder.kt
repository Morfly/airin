package io.morfly.airin

import io.morfly.airin.label.Label

data class DependencyOverride(val label: Label, val configuration: String? = null)

typealias StringLabel = String
typealias DependencyOverridesCollection = MutableMap<StringLabel, MutableMap<ConfigurationName?, DependencyOverride>>

interface DependencyOverridesHolder {

    val dependencyOverrides: DependencyOverridesCollection

    fun onDependency(
        label: String,
        configuration: String? = null,
        override: DependencyOverrideContext.() -> DependencyOverride
    ) {
        require(label.isNotBlank()) { "Dependency label can't be blank!" }

        val context = DependencyOverrideContext()
        dependencyOverrides.getOrPut(label, ::mutableMapOf)[configuration] = context.override()
    }

    fun <D : Label> onDependency(
        label: D,
        configuration: String? = null,
        override: DependencyOverrideContext.() -> DependencyOverride
    ) {
        val context = DependencyOverrideContext()
        dependencyOverrides.getOrPut(label.toString(), ::mutableMapOf)[configuration] = context.override()
    }
}

class DependencyOverrideContext {

    fun overrideWith(label: Label, configuration: String? = null): DependencyOverride =
        DependencyOverride(label, configuration)
}
