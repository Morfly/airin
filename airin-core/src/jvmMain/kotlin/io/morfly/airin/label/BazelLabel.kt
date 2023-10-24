package io.morfly.airin.label

import java.io.Serializable

data class BazelLabel(
    val workspace: String?,
    val path: String,
    val target: String?
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
}
