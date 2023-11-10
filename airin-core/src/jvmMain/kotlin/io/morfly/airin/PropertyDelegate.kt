package io.morfly.airin

import java.io.Serializable
import kotlin.reflect.KProperty

class PropertyDelegate<V>(val defaultValue: V) : Serializable {

    operator fun provideDelegate(
        holder: PropertiesHolder, property: KProperty<*>
    ): PropertyDelegate<V> {
        require(property.name !in holder.properties) {
            "Property ${property.name} already exists in ${holder::class.simpleName}"
        }
        if (defaultValue != null) {
            holder.properties[property.name] = defaultValue
        } else {
            holder.properties.remove(property.name)
        }
        return this
    }

    operator fun getValue(holder: PropertiesHolder, property: KProperty<*>): V {
        @Suppress("UNCHECKED_CAST")
        return holder.properties[property.name] as V
    }

    operator fun setValue(holder: PropertiesHolder, property: KProperty<*>, value: V) {
        if (value != null) {
            holder.properties[property.name] = value
        } else {
            holder.properties.remove(property.name)
        }
    }
}

fun <V> PropertiesHolder.property(default: V): PropertyDelegate<V> {
    return PropertyDelegate(default)
}
