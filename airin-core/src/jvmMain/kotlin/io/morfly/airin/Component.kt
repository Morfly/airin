package io.morfly.airin

abstract class Component<P : Module> : HasId, PropertiesHolder {

    final override val properties = mutableMapOf<String, Any>()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Component<*>) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode() = id.hashCode()
}
