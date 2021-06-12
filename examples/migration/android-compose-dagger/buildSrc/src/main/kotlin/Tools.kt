import org.gradle.api.Project
import org.morfly.airin.GradleStandaloneTemplateProvider
import org.morfly.airin.starlark.elements.StarlarkFile
import template.tools_android_build
import template.tools_java_build
import template.tools_kotlin_build


class Tools : GradleStandaloneTemplateProvider() {

    override fun provide(target: Project, relativePath: String): List<StarlarkFile> = listOf(
        tools_java_build(
            toolsDir = TOOLS_DIR,
            javaToolchainTargetName = JAVA_TOOLCHAIN_TARGET
        ),
        tools_kotlin_build(
            toolsDir = TOOLS_DIR,
            kotlinToolchainTargetName = KOTLIN_TOOLCHAIN_TARGET,
            kotlinVersion = KOTLIN_LANG_VERSION
        ),
        tools_android_build(
            toolsDir = TOOLS_DIR,
            composePluginTargetName = COMPOSE_PLUGIN_TARGET,
            roomPluginLibraryTargetName = ROOM_PLUGIN_LIBRARY_TARGET
        )
    )

    companion object {
        const val TOOLS_DIR = "tools"
        const val KOTLIN_LANG_VERSION = "1.5"

        const val JAVA_TOOLCHAIN_TARGET = "java_toolchain"
        const val KOTLIN_TOOLCHAIN_TARGET = "kotlin_toolchain"
        const val COMPOSE_PLUGIN_TARGET = "jetpack_compose_compiler_plugin"
        const val ROOM_PLUGIN_LIBRARY_TARGET = "androidx_room_room_compiler_library"
    }
}