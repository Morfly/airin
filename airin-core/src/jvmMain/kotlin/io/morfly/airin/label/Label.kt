package io.morfly.airin.label

interface Label {

    override fun toString(): String

    fun asComparable(): Label

    fun asBazelLabel(): BazelLabel?
}
