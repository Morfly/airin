package com.morfly.airin.legacy.migration.old

import com.morlfy.airin.starlark.elements.StarlarkFile
import org.gradle.api.Project


interface ModuleDescriptor {

    fun canDescribe(project: Project): Boolean

    fun describe(project: Project): StarlarkFile
}
