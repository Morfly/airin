rootProject.name = "airin"

include(
    ":airin-gradle-plugin",
    ":airin-starlark"
)

includeBuild("../../bazel-gen")