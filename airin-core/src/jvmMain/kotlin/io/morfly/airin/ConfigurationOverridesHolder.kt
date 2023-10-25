package io.morfly.airin

data class ConfigurationOverride(val configuration: String)

typealias ConfigurationName = String

interface ConfigurationOverridesHolder {

    val configurationOverrides: MutableMap<ConfigurationName, ConfigurationOverride>

    fun onConfiguration(
        configuration: String,
        override: ConfigurationOverrideContext.() -> ConfigurationOverride
    ) {
        configurationOverrides[configuration] = ConfigurationOverrideContext().override()
    }
}

class ConfigurationOverrideContext {

    fun overrideWith(configuration: String): ConfigurationOverride =
        ConfigurationOverride(configuration)
}
