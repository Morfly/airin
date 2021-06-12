import org.gradle.api.Project
import org.morfly.airin.GradleStandaloneTemplateProvider
import org.morfly.airin.starlark.elements.StarlarkFile
import template.artifacts_build
import java.io.File


class Artifacts : GradleStandaloneTemplateProvider() {

    override fun provide(target: Project, relativePath: String): List<StarlarkFile> {
        addArtifactsToTheProject(target.projectDir.path)

        return listOf(
            artifacts_build(
                artifactsDir = ARTIFACTS_DIR,
                roomRuntimeTargetName = ROOM_RUNTIME_TARGET,
                roomKtxTargetName = ROOM_KTX_TARGET,
                kotlinReflectTargetName = KOTLIN_REFLECT_TARGET
            )
        )
    }

    // side effect // FIXME find another solution to avoid direct file writes here as side effects.
    private fun addArtifactsToTheProject(rootProjectDir: String) {
        File("$rootProjectDir/$ARTIFACTS_SOURCE_DIR").copyRecursively(
            target = File("$rootProjectDir/$ARTIFACTS_DIR"),
            overwrite = true
        )
    }

    companion object {
        const val ARTIFACTS_DIR = "artifacts"
        const val ARTIFACTS_SOURCE_DIR = "buildSrc/artifacts"

        const val ROOM_RUNTIME_TARGET = "room_runtime"
        const val ROOM_KTX_TARGET = "room_ktx"
        const val KOTLIN_REFLECT_TARGET = "kotlin_reflect"
    }
}