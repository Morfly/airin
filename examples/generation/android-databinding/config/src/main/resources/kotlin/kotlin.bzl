load(
    "@io_bazel_rules_kotlin//kotlin:kotlin.bzl",
    _kt_jvm_library = "kt_jvm_library"
)


def _kt_android_artifact(name, srcs = [], deps = [], plugins = [], enable_data_binding = False, **kwargs):
    """Delegates Android related build attributes to the native rules but uses the Kotlin builder to compile Java and
    Kotlin srcs. Returns a sequence of labels that a wrapping macro should export.
    """
    base_name = name + "_base"
    kt_name = name + "_kt"

    base_deps = deps + ["//tools/android:android_sdk"]

    native.android_library(
        name = base_name,
        visibility = ["//visibility:private"],
        exports = base_deps,
        deps = deps if enable_data_binding else [],
        enable_data_binding = enable_data_binding,
        **kwargs
    )
    _kt_jvm_library(
        name = kt_name,
        srcs = srcs,
        deps = base_deps + [base_name],
        plugins = plugins,
        testonly = kwargs.get("testonly", default = 0),
        visibility = ["//visibility:private"],
    )
    return [base_name, kt_name]

def kt_android_library(name, exports = [], visibility = None, **kwargs):
    """Creates an Android sandwich library.

    `srcs`, `deps`, `plugins` are routed to `kt_jvm_library` the other android
    related attributes are handled by the native `android_library` rule.
    """
    native.android_library(
        name = name,
        exports = exports + _kt_android_artifact(name, **kwargs),
        visibility = visibility,
        tags = kwargs.get("tags", default = None),
        testonly = kwargs.get("testonly", default = 0),
    )