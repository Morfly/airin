/*
 * Copyright 2023 Pavlo Stavytskyi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
