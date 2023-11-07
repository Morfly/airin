package io.morfly.airin

import io.morfly.airin.label.BazelLabel
import io.morfly.airin.label.Label

typealias StringLabel = String
typealias DependencyOverrideCollection = MutableMap<StringLabel, MutableMap<ConfigurationName?, DependencyOverride>>

interface DependencyOverridesHolder {

    val dependencyOverrides: DependencyOverrideCollection

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
        require(label !is BazelLabel) {
            "Bazel labels are not allowed to be overridden!"
        }
        val context = DependencyOverrideContext()
        val labelKey = label.asShortLabel().toString()
        dependencyOverrides.getOrPut(labelKey, ::mutableMapOf)[configuration] = context.override()
    }
}

class DependencyOverrideContext {

    fun overrideWith(label: Label, newConfiguration: String? = null): DependencyOverride.Override =
        DependencyOverride.Override(label, newConfiguration)

    fun ignore(): DependencyOverride.Ignore =
        DependencyOverride.Ignore
}

sealed interface DependencyOverride {

    data class Override(val label: Label, val configuration: String? = null) : DependencyOverride

    object Ignore : DependencyOverride
}
