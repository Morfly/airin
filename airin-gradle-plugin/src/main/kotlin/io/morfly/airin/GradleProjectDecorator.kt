package io.morfly.airin

import org.gradle.api.Project

interface GradleProjectDecorator {

    fun GradleProject.decorate(target: Project)
}
