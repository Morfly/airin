//package com.morfly.airin
//
//import com.morfly.airin.java.JavaApplicationTemplateProvider
//import com.morfly.airin.java.JavaRootBuildTemplateProvider
//import com.morfly.airin.java.JavaWorkspaceTemplateProvider
//import com.morfly.airin.lib.MigratorToBazel
//import org.gradle.api.Project
//import org.morfly.bazelgen.generator.file.BazelFile
//import org.morfly.bazelgen.generator.writer.BazelFileWriter
//
//
//class GradleMigratorToBazel(
//    private val project: Project,
//    private val writer: BazelFileWriter = BazelFileWriter
//) : MigratorToBazel {
//
//    private val workspaceDir = project.rootDir
//
//    private val singletonTemplateProviders = listOf(
//        JavaWorkspaceTemplateProvider(),
//        JavaRootBuildTemplateProvider()
//    )
//    private val moduleTemplateProviders = listOf(
//        JavaApplicationTemplateProvider()
//    )
//
//    override fun migrate() {
//        project.allprojects
//            .flatMap(::provideTemplates)
//            .forEach(::writeBazelFile)
//
////        singletonTemplateProviders
////            .flatMap { it.provideTemplates(project.rootProject) }
////            .forEach(::writeBazelFile)
//    }
//
//    private fun provideTemplates(module: Project): List<BazelFile> {
//        val templates = moduleTemplateProviders
//            .firstOrNull { it.canProvide(module) }
//            ?.provideTemplates(module)
//
//        if (templates == null) {
//            // show warning
//        }
//        return templates ?: emptyList()
//    }
//
//    private fun writeBazelFile(bazelFile: BazelFile) {
//        println("Bazel File Dir: ${bazelFile.relativePath}")
//
////        writer.write(workspaceDir, bazelFile)
//    }
//}