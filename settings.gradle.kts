rootProject.name = "airin"

include(
    ":airin-gradle-plugin",
    ":airin-starlark"
)
include("airin-migration:gradle-plugin")
include("airin-migration:core")
