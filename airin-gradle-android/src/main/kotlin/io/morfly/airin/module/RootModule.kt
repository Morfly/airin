package io.morfly.airin.module

import io.morfly.airin.GradlePackageComponent
import io.morfly.airin.GradleProject
import io.morfly.airin.PackageContext
import io.morfly.pendant.starlark.lang.context.BUILD
import io.morfly.pendant.starlark.lang.context.WORKSPACE
import io.morfly.pendant.starlark.lang.context.bazel
import io.morfly.pendant.starlark.lang.context.bzl
import io.morfly.pendant.starlark.workspace
import org.gradle.api.Project

abstract class RootModule : GradlePackageComponent() {

    override fun canProcess(target: Project): Boolean {
        return target.rootProject == target
    }

    override fun PackageContext.onInvoke(packageDescriptor: GradleProject) {
        val build = BUILD.bazel {
            _id = ID_BUILD
        }

        val workspace = WORKSPACE {
            _id = ID_WORKSPACE

            workspace(name = packageDescriptor.name)

            load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

            _checkpoint(CHECKPOINT_WORKSPACE_IMPORTS)
        }

        val mavenDependencies = "maven_dependencies".bzl {
            _id = ID_MAVEN_DEPENDENCIES_BZL

            val MAVEN_ARTIFACTS by listOf(
                "test"
            )
        }

        val thirdPartyBuild = BUILD.bazel {
            _id = ID_THIRD_PARTY_BUILD
        }

        generate(build, workspace)
        generate(thirdPartyBuild, mavenDependencies, relativeDirPath = "third_party")
    }

    companion object {
        const val ID_WORKSPACE = "workspace"
        const val ID_BUILD = "root_build"
        const val ID_MAVEN_DEPENDENCIES_BZL = "root_maven_dependencies_bzl"
        const val ID_THIRD_PARTY_BUILD = "root_third_party_build"
        const val CHECKPOINT_WORKSPACE_IMPORTS = "${ID_WORKSPACE}_imports"
    }
}
