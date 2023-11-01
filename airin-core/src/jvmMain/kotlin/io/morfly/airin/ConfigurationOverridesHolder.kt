package io.morfly.airin

data class ConfigurationOverride(val configuration: String)

typealias ConfigurationName = String
typealias ConfigurationOverrideCollection = MutableMap<ConfigurationName, MutableList<ConfigurationOverride>>

interface ConfigurationOverridesHolder {

    val configurationOverrides: ConfigurationOverrideCollection

    fun onConfiguration(
        configuration: String,
        override: ConfigurationOverrideContext.() -> Unit
    ) {
        val overrides = ConfigurationOverrideContext().apply(override).configurationOverrides
        configurationOverrides.getOrPut(configuration, ::mutableListOf) += overrides
    }
}

class ConfigurationOverrideContext {

    val configurationOverrides: MutableList<ConfigurationOverride> = mutableListOf()

    fun overrideWith(newConfiguration: String) {
        configurationOverrides += ConfigurationOverride(newConfiguration)
    }
}
