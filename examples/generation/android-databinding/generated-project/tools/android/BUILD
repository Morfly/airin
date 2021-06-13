java_plugin(
    name = "compiler_annotation_processor",
    processor_class = "android.databinding.annotationprocessor.ProcessDataBinding",
    generates_api = True,
    deps = ["@bazel_tools//src/tools/android/java/com/google/devtools/build/android:all_android_tools"],
    visibility = ["//visibility:public"],
)

java_import(
    name = "android_sdk",
    jars = ["@bazel_tools//tools/android:android_jar"],
    visibility = ["//visibility:public"],
    neverlink = True,
)