package io.morfly.airin

import io.morfly.airin.label.MavenCoordinates

abstract class Component<P : PackageDescriptor> : HasId, PropertiesHolder, SharedPropertiesHolder {

    final override val properties = mutableMapOf<String, Any?>()

    final override lateinit var sharedProperties: MutableMap<String, Any?>
        @InternalAirinApi set

    override val sharedPropertiesAvailable
        get() = ::sharedProperties.isInitialized

    val allMavenArtifacts: MutableSet<MavenCoordinates> by sharedProperty(mutableSetOf())

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Component<*>) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode() = id.hashCode()
}
