load("//tools/kotlin:kotlin.bzl", "kt_android_library")
load("@rules_jvm_external//:defs.bzl", "artifact")

package(default_visibility = ["//visibility:public"])

DATABINDING_LAYOUTS = ["src/main/res/layout/layout_lib1.xml"]

VIEW_MODELS_WITH_RES_IMPORTS = []

VIEW_MODELS = ["src/main/kotlin/com/morfly/airin/sample/lib1/Lib1ViewModel.kt"] + [
    "modify_imports_in_" + view_model_with_res_imports[0:-3]
    for view_model_with_res_imports in VIEW_MODELS_WITH_RES_IMPORTS
]

[
    genrule(
        name = "modify_imports_in_" + file[0:-3],
        srcs = [file],
        outs = [file[0:-3] + "_synthetic.kt"],
        cmd = """
        cat $(SRCS) |
        sed 's/import com.morfly.airin.sample.lib1.R/import com.morfly.airin.sample.lib1.viewmodels.R/g' > $(OUTS)
        """,
    )
    for file in VIEW_MODELS_WITH_RES_IMPORTS
]

BINDING_ADAPTERS = ["src/main/kotlin/com/morfly/airin/sample/lib1/BindingAdapters.java"]

EXCLUDED_LIB1_FILES = VIEW_MODELS + VIEW_MODELS_WITH_RES_IMPORTS + BINDING_ADAPTERS

LIB1_FILES_WITH_RESOURCE_IMPORTS = glob(
    [
        "src/main/kotlin/**/*.kt",
        "src/main/kotlin/**/*.java",
    ],
    exclude = EXCLUDED_LIB1_FILES,
)

LIB1_FILES = [
    "modify_imports_in_" + app_files_with_res_imports[0:-3]
    for app_files_with_res_imports in LIB1_FILES_WITH_RESOURCE_IMPORTS
]

[
    genrule(
        name = "modify_imports_in_" + file[0:-3],
        srcs = [file],
        outs = [file[0:-3] + "_synthetic.kt"],
        cmd = """
        cat $(SRCS) |
        sed 's/import com.morfly.airin.sample.lib1.databinding./import com.morfly.airin.sample.lib1.databinding.databinding./g' > $(OUTS)
        """,
    )
    for file in LIB1_FILES_WITH_RESOURCE_IMPORTS
]

android_library(
    name = "resources",
    custom_package = "com.morfly.airin.sample.lib1.res",
    manifest = "src/main/AndroidManifest.xml",
    exports_manifest = True,
    resource_files = glob(
        ["src/main/res/**"],
        exclude = DATABINDING_LAYOUTS,
    ),
    deps = [
        "//lib2",
        "//lib3",
    ],
)

kt_android_library(
    name = "view_models",
    srcs = VIEW_MODELS,
    custom_package = "com.morfly.airin.sample.lib1.viewmodels",
    manifest = "src/main/ViewModelsManifest.xml",
    enable_data_binding = True,
    deps = [
        ":resources",
        artifact("androidx.databinding:databinding-common"),
        artifact("androidx.databinding:databinding-adapters"),
        artifact("androidx.databinding:databinding-runtime"),
        artifact("androidx.annotation:annotation"),
    ],
)

android_library(
    name = "databinding_resources",
    srcs = BINDING_ADAPTERS,
    custom_package = "com.morfly.airin.sample.lib1.databinding",
    manifest = "src/main/DataBindingResourcesManifest.xml",
    exports_manifest = True,
    resource_files = DATABINDING_LAYOUTS,
    enable_data_binding = True,
    deps = [
        ":resources",
        ":view_models",
        artifact("androidx.databinding:databinding-adapters"),
        artifact("androidx.databinding:databinding-common"),
        artifact("androidx.databinding:databinding-runtime"),
    ],
)

kt_android_library(
    name = "lib1",
    srcs = LIB1_FILES,
    custom_package = "com.morfly.airin.sample.lib1",
    manifest = "src/main/AndroidManifest.xml",
    exports_manifest = True,
    enable_data_binding = True,
    deps = [
        ":resources",
        ":view_models",
        ":databinding_resources",
        artifact("androidx.databinding:databinding-common"),
        artifact("androidx.databinding:databinding-adapters"),
        artifact("androidx.databinding:databinding-runtime"),
        artifact("androidx.annotation:annotation"),
    ],
    visibility = ["//visibility:public"],
)
