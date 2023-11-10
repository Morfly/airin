package io.morfly.airin.module

import include
import io.morfly.airin.ModuleComponent
import io.morfly.airin.GradleModule
import io.morfly.airin.ModuleContext
import io.morfly.airin.applyDependenciesFrom
import io.morfly.airin.feature.ArtifactMappingFeature
import io.morfly.airin.androidMetadata
import io.morfly.pendant.starlark.glob
import io.morfly.pendant.starlark.kt_android_library
import io.morfly.pendant.starlark.lang.context.BUILD
import io.morfly.pendant.starlark.lang.context.bazel
import org.gradle.api.Project

abstract class AndroidLibraryModule : ModuleComponent() {

    init {
        include<ArtifactMappingFeature>()
    }

    override fun canProcess(target: Project): Boolean = with(target.plugins) {
        hasPlugin("com.android.library") || hasPlugin("com.android.application")
    }

    override fun ModuleContext.onInvoke(module: GradleModule) {
        val build = BUILD.bazel {
            _id = ID_BUILD

            load("@io_bazel_rules_kotlin//kotlin:android.bzl", "kt_android_library")
            load("@rules_jvm_external//:defs.bzl", "artifact")

            kt_android_library {
                _id = ID_BUILD_TARGET_CALL

                name = module.name
                srcs = glob("src/main/**/*.kt")
                custom_package = module.androidMetadata?.packageName
                manifest = "src/main/AndroidManifest.xml"
                resource_files = glob("src/main/res/**")
                visibility = list["//visibility:public"]

                applyDependenciesFrom(module)
            }
        }
        generate(build)
    }

    companion object {
        const val ID_BUILD = "android_library_build"
        const val ID_BUILD_TARGET_CALL = "android_library_target_call"
    }
}
