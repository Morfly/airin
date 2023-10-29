package io.morfly.airin.label

import java.io.Serializable

data class BazelLabel(
    val workspace: String? = null,
    val path: String,
    val target: String? = null
) : Label, Serializable {

    private val stringLabel by lazy {
        buildString {
            if (workspace != null) {
                append("@")
                append(workspace)
            }
            append("//")
            append(path)
            if (target != null) {
                append(":")
                append(target)
            }
        }
    }

    override fun toString() = stringLabel

    override fun asComparable(): Label = this

    override fun asBazelLabel(): BazelLabel = this
}
