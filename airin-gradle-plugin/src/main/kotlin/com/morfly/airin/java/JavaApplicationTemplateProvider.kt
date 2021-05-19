//package com.morfly.airin.java
//
//import com.morfly.airin.APPLICATION
//import com.morfly.airin.lib.ModuleTemplateProvider
//import com.morfly.airin.template.java.java_build_file
//import org.gradle.api.Project
//import org.morfly.bazelgen.generator.file.BazelFile
//
//
//class JavaApplicationTemplateProvider : ModuleTemplateProvider<Project>() {
//
//    override fun canProvide(module: Project): Boolean =
//        module.plugins.hasPlugin(APPLICATION)
//
//    override fun provideTemplates(module: Project): List<BazelFile> {
//        val relativePath = module.rootProject.relativePath(module.projectDir)
//        val template = java_build_file(relativePath, module.name)
//        return listOf(template)
//    }
//}