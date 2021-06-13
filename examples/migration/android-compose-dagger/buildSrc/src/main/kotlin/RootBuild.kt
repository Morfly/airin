import org.gradle.api.Project
import org.morfly.airin.GradleStandaloneTemplateProvider
import org.morfly.airin.starlark.elements.StarlarkFile
import template.root_build

class RootBuild : GradleStandaloneTemplateProvider() {

    override fun provide(target: Project, relativePath: String): List<StarlarkFile> = listOf(
        root_build(
            toolsDir = Tools.TOOLS_DIR,
            artifactsDir = Artifacts.ARTIFACTS_DIR,
            javaToolchainTarget = Tools.JAVA_TOOLCHAIN_TARGET,
            kotlinToolchainTarget = Tools.KOTLIN_TOOLCHAIN_TARGET,
            roomRuntimeTarget = Artifacts.ROOM_RUNTIME_TARGET,
            roomKtxTarget = Artifacts.ROOM_KTX_TARGET,
            kotlinReflectTarget = Artifacts.KOTLIN_REFLECT_TARGET,
            composePluginTarget = Tools.COMPOSE_PLUGIN_TARGET,
            roomPluginLibraryTarget = Tools.ROOM_PLUGIN_LIBRARY_TARGET,
            debugKeystoreFile = Workspace.DEBUG_KEYSTORE_FILE_NAME
        )
    )
}