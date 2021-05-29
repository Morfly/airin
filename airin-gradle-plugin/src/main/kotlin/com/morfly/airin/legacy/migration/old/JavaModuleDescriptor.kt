//package com.morfly.airin.legacy.migration.old
//
//import com.morfly.airin.legacy.template.java.java_build_file
//import com.morlfy.airin.starlark.elements.BazelFile
//import org.gradle.api.Project
//
//
//class JavaModuleDescriptor : ModuleDescriptor {
//
//    override fun canDescribe(project: Project): Boolean =
//        project.plugins.hasPlugin("java")
//
//    override fun describe(project: Project): BazelFile {
//        val relativePath = project.rootProject.relativePath(project.projectDir)
//        println("relativePath: $relativePath")
//        return java_build_file(relativePath, moduleName = project.name)
//    }
//}