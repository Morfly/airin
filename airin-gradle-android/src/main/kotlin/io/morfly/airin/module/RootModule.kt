package io.morfly.airin.module

import io.morfly.airin.GradleModule
import io.morfly.airin.ModuleComponent
import io.morfly.airin.ModuleContext
import io.morfly.airin.allMavenArtifacts
import io.morfly.airin.label.MavenCoordinates
import io.morfly.airin.property
import io.morfly.pendant.starlark.lang.context.BUILD
import io.morfly.pendant.starlark.lang.context.WORKSPACE
import io.morfly.pendant.starlark.lang.context.bazel
import io.morfly.pendant.starlark.lang.context.bzl
import io.morfly.pendant.starlark.workspace
import org.gradle.api.Project

abstract class RootModule : ModuleComponent() {

    val composeVersion by property("1.4.3")
    val composeMaterial3Version by property("1.1.1")

    init {
        shared = true
    }

    override fun canProcess(project: Project): Boolean =
        project.plugins.hasPlugin("io.morfly.airin.android")

    override fun ModuleContext.onInvoke(module: GradleModule) {
        val build = BUILD.bazel {
            _id = ID_BUILD
        }

        val workspace = WORKSPACE {
            _id = ID_WORKSPACE

            workspace(name = module.name)

            load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")
        }

        val mavenDependencies = "maven_dependencies".bzl {
            _id = ID_MAVEN_DEPENDENCIES_BZL

            val MAVEN_ARTIFACTS by list {
                _id = ID_MAVEN_ARTIFACTS
                _sortedWith(compareBy { it })

                allMavenArtifacts
                    .map { it.withVersion() }
                    .forEach { item(it.toString()) }
            }
        }

        val thirdPartyBuild = BUILD.bazel {
            _id = ID_THIRD_PARTY_BUILD

            load("@rules_jvm_external//:defs.bzl", "artifact")
        }

        generate(build, workspace)
        generate(thirdPartyBuild, mavenDependencies, relativeDirPath = "third_party")
    }

    private fun MavenCoordinates.withVersion(): MavenCoordinates {
        if (version != null) return this
        if (!group.startsWith("androidx.compose")) return this

        return when {
            group.endsWith("material3") -> copy(version = composeMaterial3Version)
            else -> copy(version = composeVersion)
        }
    }

    companion object {
        const val ID_WORKSPACE = "workspace"
        const val ID_BUILD = "root_build"
        const val ID_MAVEN_DEPENDENCIES_BZL = "root_maven_dependencies_bzl"
        const val ID_THIRD_PARTY_BUILD = "root_third_party_build"
        const val ID_MAVEN_ARTIFACTS = "root_maven_dependencies_artifacts"
    }
}
