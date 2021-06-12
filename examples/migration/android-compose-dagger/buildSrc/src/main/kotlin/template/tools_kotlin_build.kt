@file:Suppress("FunctionName", "SpellCheckingInspection")

package template

import org.morfly.airin.starlark.lang.BUILD
import org.morfly.airin.starlark.library.define_kt_toolchain
import org.morfly.airin.starlark.library.kt_javac_options
import org.morfly.airin.starlark.library.kt_kotlinc_options


fun tools_kotlin_build(
    toolsDir: String,
    kotlinToolchainTargetName: String,
    kotlinVersion: String
    /**
     *
     */
) = BUILD("$toolsDir/kotlin") {

    load(
        "@io_bazel_rules_kotlin//kotlin:kotlin.bzl",
        "define_kt_toolchain",
        "kt_javac_options",
        "kt_kotlinc_options",
    )

    kt_kotlinc_options {
        name = "kt_kotlinc_options"
//        "x_allow_jvm_ir_dependencies" `=` True
//        "x_use_ir" `=` True
    }

    kt_javac_options {
        name = "kt_javac_options"
    }

    define_kt_toolchain(
        name = kotlinToolchainTargetName,
        api_version = kotlinVersion,
        experimental_use_abi_jars = False,
        javac_options = ":kt_javac_options",
        jvm_target = "1.8",
        kotlinc_options = ":kt_kotlinc_options",
        language_version = kotlinVersion,
    )
}