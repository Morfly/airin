package io.morfly.airin.label

import java.io.Serializable

data class GradleLabel(
    val projectPath: String,
    val task: String? = null
) : Label, Serializable {

    private val stringLabel by lazy {
        buildString {
            append(projectPath)
            if (task != null) {
                append(task)
            }
        }
    }

    override fun asComparable(): Label = this

    override fun asBazelLabel(): BazelLabel {
        return BazelLabel("")
    }

    override fun toString() = stringLabel
}
