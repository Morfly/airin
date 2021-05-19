package com.morfly.airin.migration.old

import org.gradle.api.Project
import org.morfly.bazelgen.generator.file.BazelFile


interface ModuleDescriptor {

    fun canDescribe(project: Project): Boolean

    fun describe(project: Project): BazelFile
}
