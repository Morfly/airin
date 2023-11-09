package io.morfly.airin.plugin

import io.morfly.airin.AndroidModuleDecorator

open class AirinAndroidGradlePlugin : AirinGradlePlugin() {

    override val defaultProjectDecorator = AndroidModuleDecorator::class.java

    companion object {
        const val ID = "io.morfly.airin.android"
    }
}
