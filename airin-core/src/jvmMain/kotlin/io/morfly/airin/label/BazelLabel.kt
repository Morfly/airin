package io.morfly.airin.label

import java.io.Serializable

data class BazelLabel(val label: String) : Label, Serializable {

    override fun toString() = label

    override fun asComparable(): Label = this

    override fun asBazelLabel(): BazelLabel = this
}
