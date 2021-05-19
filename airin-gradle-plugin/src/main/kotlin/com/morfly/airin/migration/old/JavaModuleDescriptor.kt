package com.morfly.airin.migration.old

import com.morfly.airin.template.java.java_build_file
import org.gradle.api.Project
import org.morfly.bazelgen.generator.file.BazelFile


class JavaModuleDescriptor : ModuleDescriptor {

    override fun canDescribe(project: Project): Boolean =
        project.plugins.hasPlugin("java")

    override fun describe(project: Project): BazelFile {
        val relativePath = project.rootProject.relativePath(project.projectDir)
        println("relativePath: $relativePath")
        return java_build_file(relativePath, moduleName = project.name)
    }
}