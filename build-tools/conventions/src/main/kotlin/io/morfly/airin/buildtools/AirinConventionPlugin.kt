package io.morfly.airin.buildtools

import org.gradle.api.Plugin
import org.gradle.api.Project

abstract class AirinConventionPlugin(
    private val body: Project.() -> Unit
) : Plugin<Project> {

    override fun apply(target: Project) = target.body()
}