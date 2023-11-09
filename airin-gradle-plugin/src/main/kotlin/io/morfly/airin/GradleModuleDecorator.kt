package io.morfly.airin

import org.gradle.api.Project

interface GradleModuleDecorator {

    fun GradleModule.decorate(target: Project)
}
