package io.morfly.airin.module

import io.morfly.airin.GradlePackageComponent
import io.morfly.airin.GradleProject
import io.morfly.airin.PackageContext
import io.morfly.pendant.starlark.glob
import io.morfly.pendant.starlark.kt_android_library
import io.morfly.pendant.starlark.lang.context.BUILD
import io.morfly.pendant.starlark.lang.context.bazel
import org.gradle.api.Project

abstract class AndroidLibraryModule: GradlePackageComponent() {

    override fun canProcess(target: Project): Boolean {
        return false
//        return target.plugins.hasPlugin("com.android.library")
    }

    override fun PackageContext.onInvoke(packageDescriptor: GradleProject) {
        BUILD.bazel {
            _id = "android_library_build"

            load("@io_bazel_rules_kotlin//kotlin:android.bzl", "kt_android_library")
            load("@rules_jvm_external//:defs.bzl", "artifact")

            kt_android_library(
                name = "navigation",
                srcs = glob(
                    "src/main/**/*.kt",
                    "src/debug/**/*.kt",
                ),
                custom_package = "com.turo.navigation",
                manifest = "src/main/AndroidManifest.xml",
                plugins = list["//tools/ksp:deeplink_dispatch"],
                resource_files = glob(
                    "src/main/res/**",
                    "src/debug/res/**",
                ),
                visibility = list["//visibility:public"],
            )
        }
    }
}
