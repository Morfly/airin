package io.morfly.airin

import io.morfly.airin.label.Label
import io.morfly.pendant.starlark.lang.ModifierCollection
import io.morfly.pendant.starlark.lang.ModifiersHolder

class FeatureContext(
    override val sharedProperties: MutableMap<String, Any?> = mutableMapOf()
) : ModifiersHolder, DependencyOverridesHolder, ConfigurationOverridesHolder, AddedDependencyHolder,
    SharedPropertiesHolder {

    override val modifiers: ModifierCollection = linkedMapOf()

    override val dependencyOverrides: DependencyOverrideCollection = mutableMapOf()

    override val configurationOverrides: ConfigurationOverrideCollection = mutableMapOf()

    override val addedDependencies = mutableMapOf<ConfigurationName, MutableSet<Label>>()
}
