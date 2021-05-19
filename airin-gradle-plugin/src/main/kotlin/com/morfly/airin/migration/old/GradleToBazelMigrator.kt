package com.morfly.airin.migration.old

import com.morfly.airin.template.java.java_root_build_file
import com.morfly.airin.template.java.java_workspace_file
import org.gradle.api.Project
import org.morfly.bazelgen.generator.file.BazelFile
import org.morfly.bazelgen.generator.writer.BazelFileWriter


class GradleToBazelMigrator(
    private val project: Project,
    private val writer: BazelFileWriter = BazelFileWriter
) {

    private val descriptorsHolder = ModuleDescriptorsHolder(
        listOf(JavaModuleDescriptor())
    )
    private val workspaceDir = project.rootProject.projectDir

    fun migrate() {
        println("Workspace Dir: $workspaceDir")
        project.allprojects
            .mapNotNull { descriptorsHolder.describe(it) }
            .forEach(::writeBuildFile)
        createWorkspaceFile()
        createRootBuildFile()
    }

    private fun writeBuildFile(bazelFile: BazelFile) {
        println("Build Dir: ${bazelFile.relativePath}")

        writer.write(workspaceDir, bazelFile)
    }

    private fun createWorkspaceFile() {
        writer.write(workspaceDir, java_workspace_file())
    }

    private fun createRootBuildFile() {
        writer.write(workspaceDir, java_root_build_file("application", "com.morfly.example.Main"))
    }
}