//package com.morfly.airin.java
//
//import com.morfly.airin.lib.TemplateProvider
//import com.morfly.airin.template.java.java_workspace_file
//import org.gradle.api.Project
//import org.morfly.bazelgen.generator.file.BazelFile
//
//
//class JavaWorkspaceTemplateProvider : TemplateProvider<Project>() {
//
//    override fun provideTemplates(root: Project): List<BazelFile> =
//        listOf(java_workspace_file())
//}