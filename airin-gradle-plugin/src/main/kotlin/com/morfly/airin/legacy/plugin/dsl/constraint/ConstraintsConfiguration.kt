package com.morfly.airin.legacy.plugin.dsl.constraint

import org.gradle.api.Action
import org.gradle.api.Project


open class ConstraintsConfiguration : BaseConstraintsConfiguration() {

    fun build(action: Action<FileConstraintsConfiguration>) {

    }

    fun workspace(action: Action<FileConstraintsConfiguration>) {

    }

    fun computeConstraints(): List<Project> {
        return emptyList()
    }
}

open class BaseConstraintsConfiguration {

    fun projects(vararg args: Any) {
        args.size
    }

    fun subprojectsOf(vararg args: Any) {
        args.size
    }

    fun ignore(action: Action<IgnoreConstraintsConfiguration>) {
    }
}
