import org.gradle.api.Project
import org.morfly.airin.*
import org.morfly.airin.starlark.elements.StarlarkFile
import template.RoomInfo
import template.kotlin_android_build


class KotlinAndroidBuild : GradlePerModuleTemplateProvider() {

    override fun canProvide(target: Project): Boolean =
        with(target.plugins) {
            hasPlugin("kotlin-android")
                    && (hasPlugin(ANDROID_LIBRARY) || hasPlugin(ANDROID_APPLICATION))
        }


    override fun provide(target: Project, relativePath: String): List<StarlarkFile> {
        val implArtifacts = target.artifactDependencies(setOf("implementation"))
        val kaptArtifacts = target.artifactDependencies(setOf("kapt"))
        val implModules = target.moduleDependencies(setOf("implementation"))
        val apiModules = target.moduleDependencies(setOf("api"))

        val hasRoom = implArtifacts.any { it.name.startsWith("room") }
        val hasDagger = kaptArtifacts.any { it.name.startsWith("dagger") }

        val manifest = target.relativePath(target.manifest!!)

        val formattedExternalDeps = implArtifacts.asSequence()
            .filter { it !in sharedData.ignoredArtifacts }
            .filter { !it.name.startsWith("room") }
            .map { it.toString(includeVersion = false) }
            .toList()
        val formattedExports = apiModules.map { it.bazelLabel() }
        val formattedInternalDeps = mutableSetOf<String>().also { deps ->
            deps += implModules.map { it.bazelLabel() }
            deps += formattedExports
        }

        return listOf(
            kotlin_android_build(
                targetName = target.name,
                packageName = target.packageName!!,
                hasBinary = target.plugins.hasPlugin(ANDROID_APPLICATION),
                hasCompose = target.isComposeEnabled,
                composePluginTarget = Tools.COMPOSE_PLUGIN_TARGET,
                manifestLocation = manifest,
                internalDeps = formattedInternalDeps,
                exportedTargets = formattedExports,
                externalDeps = formattedExternalDeps,
                kotlinReflectTarget = Artifacts.KOTLIN_REFLECT_TARGET,
                hasDagger = hasDagger,
                roomDeps = if (hasRoom) RoomInfo(
                    roomCompilerLibraryTaget = Tools.ROOM_PLUGIN_LIBRARY_TARGET,
                    roomRuntimeTarget = Artifacts.ROOM_RUNTIME_TARGET,
                    roomKtxTarget = Artifacts.ROOM_KTX_TARGET,
                ) else null
            )
        )
    }

}