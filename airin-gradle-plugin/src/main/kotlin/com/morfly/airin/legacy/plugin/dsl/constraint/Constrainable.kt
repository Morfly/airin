package com.morfly.airin.legacy.plugin.dsl.constraint

import org.gradle.api.Action


interface Constrainable {

    val constraints: ConstraintsConfiguration

    fun constraints(action: Action<ConstraintsConfiguration>)
}


