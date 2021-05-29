//package com.morfly.airin.java
//
//import com.morfly.airin.legacy.TemplateProvider
//import com.morfly.airin.legacy.java_root_build_file
//import org.gradle.api.Project
//import org.morfly.bazelgen.generator.file.BazelFile
//
//
//class JavaRootBuildTemplateProvider : TemplateProvider<Project>() {
//
//    override fun provideTemplates(root: Project): List<BazelFile> =
//        listOf(java_root_build_file("application", "com.morfly.example.Main"))
//}