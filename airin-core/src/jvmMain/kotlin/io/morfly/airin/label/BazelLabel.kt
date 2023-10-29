package io.morfly.airin.label

import java.io.Serializable

data class BazelLabel(val label: String) : Label, Serializable {

    override fun toString() = label

    override fun shorten(): Label = this
}
