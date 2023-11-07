package io.morfly.airin

import org.gradle.api.Project

interface GradleProjectDecorator {

    fun GradleModule.decorate(target: Project) = Unit
}
