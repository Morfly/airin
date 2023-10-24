package io.morfly.airin.label

import java.io.Serializable

data class MavenCoordinates(
    val group: String?,
    val name: String,
    val version: String?
) : Label, Serializable {

    override fun toString() = buildString {
        if (group != null) {
            append(group)
            append(':')
        }
        append(name)
        if (version != null) {
            append(':')
            append(version)
        }
    }
}
