package io.morfly.airin

import io.morfly.airin.label.BazelLabel
import io.morfly.airin.label.Label

typealias StringLabel = String
typealias DependencyOverrideCollection = MutableMap<StringLabel, MutableMap<ConfigurationName?, MutableList<DependencyOverride>>>

interface DependencyOverridesHolder {

    val dependencyOverrides: DependencyOverrideCollection

    fun onDependency(
        label: String,
        configuration: String? = null,
        override: DependencyOverrideContext.() -> Unit
    ) {
        require(label.isNotBlank()) { "Dependency label can't be blank!" }

        val overrides = DependencyOverrideContext().apply(override).dependencyOverrides
        dependencyOverrides
            .getOrPut(label, ::mutableMapOf)
            .getOrPut(configuration, ::mutableListOf) += overrides
    }

    fun <D : Label> onDependency(
        label: D,
        configuration: String? = null,
        override: DependencyOverrideContext.() -> Unit
    ) {
        require(label !is BazelLabel) {
            "Bazel labels are not allowed to be overridden!"
        }
        val overrides = DependencyOverrideContext().apply(override).dependencyOverrides
        val stringLabel = label.asShortLabel().toString()
        dependencyOverrides
            .getOrPut(stringLabel, ::mutableMapOf)
            .getOrPut(configuration, ::mutableListOf) += overrides
    }
}

class DependencyOverrideContext {

    val dependencyOverrides = mutableListOf<DependencyOverride>()

    fun overrideWith(label: Label, newConfiguration: String? = null) {
        dependencyOverrides += DependencyOverride(label, newConfiguration)
    }
}

data class DependencyOverride(val label: Label, val configuration: String? = null)
