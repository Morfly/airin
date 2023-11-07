package io.morfly.airin

import java.io.Serializable
import kotlin.reflect.KProperty

class SharedPropertyDelegate<V>(val defaultValue: V) : Serializable {

    operator fun provideDelegate(
        holder: SharedPropertiesHolder, property: KProperty<*>
    ): SharedPropertyDelegate<V> = this

    operator fun getValue(holder: SharedPropertiesHolder, property: KProperty<*>): V {
        validate(holder, property.name)
        @Suppress("UNCHECKED_CAST")
        return if (defaultValue != null) {
            holder.sharedProperties.getOrPut(property.name) { defaultValue }
        } else {
            holder.sharedProperties[property.name]
        } as V
    }

    operator fun setValue(holder: SharedPropertiesHolder, property: KProperty<*>, value: V) {
        validate(holder, property.name)
        if (value != null) {
            holder.sharedProperties[property.name] = value
        } else {
            holder.sharedProperties.remove(property.name)
        }
    }

    private fun validate(holder: SharedPropertiesHolder, property: String) {
        require(holder.sharedPropertiesAvailable) {
            "Shared property $property is not available during build configuration phase!"
        }
    }
}

fun <V> SharedPropertiesHolder.sharedProperty(default: V): SharedPropertyDelegate<V> {
    return SharedPropertyDelegate(default)
}
