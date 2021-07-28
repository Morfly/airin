import org.gradle.api.Project
import org.morfly.airin.*
import org.morfly.airin.starlark.elements.StarlarkFile
import template.android_module_build_template


class AndroidModuleBuild : GradlePerModuleTemplateProvider() {

    override fun canProvide(target: Project): Boolean = with(target.plugins) {
        hasPlugin("kotlin-android")
                && (hasPlugin(ANDROID_LIBRARY) || hasPlugin(ANDROID_APPLICATION))
    }

    override fun provide(target: Project, relativePath: String): List<StarlarkFile> {
        val moduleDeps = mutableListOf<String>()

        if (sharedData.ignoredArtifacts.any { it.group == "com.google.dagger" }) {
            moduleDeps += "//:dagger"
        }

        if (sharedData.ignoredArtifacts.any { it.name.startsWith("kotlinx-coroutines") }) {
            moduleDeps += "//:kotlin_coroutines_jvm"
        }

        moduleDeps += target
            .moduleDependencies(setOf("implementation"))
            .map { it.bazelLabel() }

        return listOf(
            android_module_build_template(
                name = target.name,
                packageName = target.packageName ?: "<ERROR>",
                hasBinary = target.plugins.hasPlugin(ANDROID_APPLICATION),
                artifactDeps = target
                    .artifactDependencies(setOf("implementation"))
                    .filter { it !in sharedData.ignoredArtifacts }
                    .asString(version = false),
                moduleDeps = moduleDeps
            )
        )
    }

}