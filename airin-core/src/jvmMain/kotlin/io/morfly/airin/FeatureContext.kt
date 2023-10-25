package io.morfly.airin

import io.morfly.pendant.starlark.lang.ModifierCollection
import io.morfly.pendant.starlark.lang.ModifiersHolder

class FeatureContext : ModifiersHolder, DependencyOverridesHolder, ConfigurationOverridesHolder {

    override val modifiers: ModifierCollection = linkedMapOf()

    override val dependencyOverrides: DependencyOverridesCollection = mutableMapOf()

    override val configurationOverrides = mutableMapOf<ConfigurationName, ConfigurationOverride>()
}
