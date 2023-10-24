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

    override fun toString() = stringLabel
}
