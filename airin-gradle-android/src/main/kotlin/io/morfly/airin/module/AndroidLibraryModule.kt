package io.morfly.airin.module

import io.morfly.airin.GradlePackageComponent
import io.morfly.airin.GradleProject
import io.morfly.airin.PackageContext
import io.morfly.pendant.starlark.glob
import io.morfly.pendant.starlark.kt_android_library
import io.morfly.pendant.starlark.lang.context.BUILD
import io.morfly.pendant.starlark.lang.context.bazel
import org.gradle.api.Project

abstract class AndroidLibraryModule : GradlePackageComponent() {

    override fun canProcess(target: Project): Boolean {
        return target.plugins.hasPlugin("com.android.library")
    }

    override fun PackageContext.onInvoke(packageDescriptor: GradleProject) {
        val build = BUILD.bazel {
            _id = ID_BUILD

            load("@io_bazel_rules_kotlin//kotlin:android.bzl", "kt_android_library")
            load("@rules_jvm_external//:defs.bzl", "artifact")

            kt_android_library {
                _id = ID_BUILD_TARGET_CALL

                name = "${packageDescriptor.name}_lib"
                srcs = glob("src/main/**/*.kt")
                custom_package = "io.morfly.airin.sample"
                manifest = "src/main/AndroidManifest.xml"
                plugins = list["//third_party:jetpack_compose_compiler_plugin"]
                resource_files = glob("src/main/res/**")
                visibility = list["//visibility:public"]
//                deps = list[
//                    artifact("androidx.compose.ui:ui"),
//                    artifact("androidx.compose.ui:ui-graphics"),
//                    artifact("androidx.compose.ui:ui-tooling-preview"),
//                    artifact("androidx.compose.material3:material3"),
//                    artifact("androidx.activity:activity-compose"),
//                    artifact("androidx.core:core-ktx"),
//                    artifact("androidx.lifecycle:lifecycle-runtime-ktx"),
//                ]
            }
        }
        generate(build)
    }

    companion object {
        const val ID_BUILD = "android_library_build"
        const val ID_BUILD_TARGET_CALL = "android_library_target_call"
    }
}
