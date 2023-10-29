package io.morfly.airin.plugin

import io.morfly.airin.AndroidProjectDecorator

open class AirinAndroidGradlePlugin : AirinGradlePlugin() {

    override val defaultProjectDecorator = AndroidProjectDecorator::class.java

    companion object {
        const val ID = "io.morfly.airin.android"
    }
}
