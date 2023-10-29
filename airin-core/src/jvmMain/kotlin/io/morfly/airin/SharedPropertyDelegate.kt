package io.morfly.airin

import java.io.Serializable
import kotlin.reflect.KProperty

class SharedPropertyDelegate<V : Any>(val defaultValue: V) : Serializable {

    operator fun provideDelegate(
        holder: SharedPropertiesHolder, property: KProperty<*>
    ): SharedPropertyDelegate<V> = this

    operator fun getValue(holder: SharedPropertiesHolder, property: KProperty<*>): V {
        validate(holder, property.name)
        @Suppress("UNCHECKED_CAST")
        return holder.sharedProperties.getOrPut(property.name) { defaultValue } as V
    }

    operator fun setValue(holder: SharedPropertiesHolder, property: KProperty<*>, value: V) {
        validate(holder, property.name)
        holder.sharedProperties[property.name] = value
    }

    private fun validate(holder: SharedPropertiesHolder, property: String) {
        require(holder.sharedPropertiesAvailable) {
            "Shared property $property is not available during build configuration phase!"
        }
    }
}

fun <V : Any> SharedPropertiesHolder.sharedProperty(default: V): SharedPropertyDelegate<V> {
    return SharedPropertyDelegate(default)
}
