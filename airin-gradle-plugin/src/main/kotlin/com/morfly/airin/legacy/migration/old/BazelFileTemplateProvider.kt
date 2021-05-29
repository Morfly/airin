//package com.morfly.airin.migration
//
//import org.gradle.api.Project
//import org.morfly.bazelgen.generator.dsl.BUILD
//import org.morfly.bazelgen.generator.file.BazelFile
//
//
//interface TemplateProvider<M, T : BazelFile> {
//
//    fun provideTemplates(module: M): List<T>
//}
//
//interface GradleTemplateProvider<T : BazelFile> : TemplateProvider<Project, T>
//
//interface SimpleTemplateProvider {
//
//}
////
////interface SimpleTemplateProvider<F : BazelFile> : TemplateProvider<F> {
////
////    val relativePath: String
////
////    fun provideTemplate(project: Project): F
////}
////
////interface ModuleTemplateProvider<F : BazelFile> : TemplateProvider<F> {
////
////    fun provideTemplate(project: Project): F
////}
////
////class BazelFileTemplateProvider<F : BazelFile> {
////
////    fun canProvide(project: Project): Boolean {
////        return true
////    }
////
////    fun provideTemplate(project: Project): F {
////        return TODO()
////    }
////
////
////}
////
////fun template() = BUILD {
////
////}