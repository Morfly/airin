package io.morfly.airin.label

interface Label {

    override fun toString(): String

    fun asShortLabel(): Label

    fun asBazelLabel(): BazelLabel?
}
