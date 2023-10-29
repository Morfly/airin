package io.morfly.airin.label

import java.io.Serializable

data class GradleLabel(
    val path: String,
    val name: String,
    val task: String? = null
) : Label, Serializable {

    private val stringLabel by lazy {
        buildString {
            append(path)
            if (task != null) {
                append(task)
            }
        }
    }

    override fun asComparable(): Label = this

    override fun asBazelLabel(): BazelLabel {
        val path = path
            .trim()
            .split(":")
            .filter(String::isNotBlank)
            .joinToString(separator = "/")
        return BazelLabel(path = path, target = name)
    }

    override fun toString() = stringLabel
}
