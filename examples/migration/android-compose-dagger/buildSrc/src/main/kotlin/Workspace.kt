@file:Suppress("SpellCheckingInspection")

import org.gradle.api.Project
import org.morfly.airin.GradleStandaloneTemplateProvider
import org.morfly.airin.starlark.elements.StarlarkFile
import org.morfly.airin.starlark.writer.FileWriter
import template.bazelrc
import template.bazelversion
import template.root_workspace
import java.io.File


class Workspace : GradleStandaloneTemplateProvider() {

    override fun provide(target: Project, relativePath: String): List<StarlarkFile> {
        val rootProjectDir = target.rootDir
        createBazelRcFile(rootProjectDir.path)
        createBazelVersionFile(rootProjectDir.path)

        val (composeArtifacts, otherArtifacts) = sharedData.allArtifacts.partition {
            it.group?.startsWith("androidx.compose") == true
        }
        return listOf(
            root_workspace(
                artifactList = otherArtifacts.map { it.toString(includeVersion = true) },
                composeArtifactsWithoutVersion = composeArtifacts.map { it.toString(includeVersion = false) }
            )
        )
    }

    // side effect // FIXME find another solution to avoid direct file writes here as side effects.
    private fun createBazelRcFile(rootProjectDir: String) {
        val bazelRc = bazelrc(javaToolchainTarget = Tools.JAVA_TOOLCHAIN_TARGET)
        val path = "$rootProjectDir/.bazelrc"
        FileWriter.write(File(path), bazelRc)
    }

    // side effect // FIXME find another solution to avoid direct file writes here as side effects.
    private fun createBazelVersionFile(rootProjectDir: String) {
        val bazelVersion = bazelversion()
        val path = "$rootProjectDir/.bazelversion"
        FileWriter.write(File(path), bazelVersion)
    }
}