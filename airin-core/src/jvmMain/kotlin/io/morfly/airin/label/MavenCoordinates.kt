package io.morfly.airin.label

import java.io.Serializable

data class MavenCoordinates(
    val group: String,
    val name: String,
    val version: String? = null
) : Label, Serializable {

    override fun toString() = buildString {
        append(group)
        append(':')
        append(name)
        if (version != null) {
            append(':')
            append(version)
        }
    }

    override fun asComparable(): Label =
        if (version != null) copy(version = null) else this

    override fun asBazelLabel(): BazelLabel? = null
}
