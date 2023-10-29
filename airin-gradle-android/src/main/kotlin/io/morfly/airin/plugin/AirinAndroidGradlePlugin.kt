package io.morfly.airin.plugin

import io.morfly.airin.AndroidProjectDecorator

class AirinAndroidGradlePlugin : AirinGradlePlugin() {

    override val defaultDecoratorClass = AndroidProjectDecorator::class.java

    companion object {
        const val ID = "io.morfly.airin.android"
    }
}
