load(
    "@io_bazel_rules_kotlin//kotlin:kotlin.bzl",
    "define_kt_toolchain",
    "kt_javac_options",
    "kt_kotlinc_options",
)

kt_kotlinc_options(
    name = "kt_kotlinc_options",
    #x_allow_jvm_ir_dependencies = True,
    #x_use_ir = True,
)

kt_javac_options(
    name = "kt_javac_options",
)

define_kt_toolchain(
    name = "kotlin_toolchain",
    api_version = "1.5",
    experimental_use_abi_jars = False,
    javac_options = ":kt_javac_options",
    jvm_target = "1.8",
    kotlinc_options = ":kt_kotlinc_options",
    language_version = "1.5",
)
