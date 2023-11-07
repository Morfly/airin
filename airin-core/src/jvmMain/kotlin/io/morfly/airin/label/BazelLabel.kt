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
                if (!workspace.startsWith("@")) append("@")
                append(workspace)
            }
            if (!path.startsWith("//")) append("//")
            append(path)
            if (target != null) {
                if (!target.startsWith(":")) append(":")
                append(target)
            }
        }
    }

    override fun toString() = stringLabel

    // TODO rename asShortLabel
    override fun asComparable(): Label = this

    override fun asBazelLabel(): BazelLabel = this
}
