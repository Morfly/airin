import io.morfly.airin.FeatureContext
import io.morfly.airin.GradleFeatureComponent
import io.morfly.airin.GradleProject
import io.morfly.airin.feature.AndroidBinaryFeature
import io.morfly.airin.feature.AndroidToolsFeature
import io.morfly.airin.feature.HiltAndroidFeature
import io.morfly.airin.feature.JetpackComposeFeature
import io.morfly.airin.module.AndroidLibraryModule
import io.morfly.airin.module.RootModule
import io.morfly.pendant.starlark.MavenInstallContext
import io.morfly.pendant.starlark.element.ListReference
import io.morfly.pendant.starlark.lang.context.BzlContext
import io.morfly.pendant.starlark.lang.context.WorkspaceContext
import io.morfly.pendant.starlark.lang.onContext
import io.morfly.pendant.starlark.lang.type.StringType

plugins {
    alias(sampleLibs.plugins.kotlin.android) apply false
    alias(sampleLibs.plugins.kotlin.jvm) apply false
    alias(sampleLibs.plugins.android.application) apply false
    alias(sampleLibs.plugins.android.library) apply false
    alias(sampleLibs.plugins.ksp) apply false
    alias(sampleLibs.plugins.hilt.android) apply false

    id("io.morfly.airin.android") version sampleLibs.versions.airin
}

// If modifying components don't forget to run ./gradlew publishToMavenLocal --no-configuration-cache
// Run ./gradlew simple-android:migrateToBazel to generate Bazel files.
airin {
    register<AndroidLibraryModule> {
        include<AndroidBinaryFeature>()
        include<JetpackComposeFeature>()
        include<HiltAndroidFeature>()
    }
    register<RootModule> {
        include<AndroidToolsFeature>()
        include<ForcedMavenArtifactsFeature>()
    }
}

abstract class ForcedMavenArtifactsFeature : GradleFeatureComponent() {
    override fun canProcess(target: Project) = true

    override fun FeatureContext.onInvoke(packageDescriptor: GradleProject) {
        onContext<BzlContext>(id = RootModule.ID_MAVEN_DEPENDENCIES_BZL) {

            val FORCED_MAVEN_DEPENDENCIES by list[
                "androidx.compose.compiler:compiler:1.4.7",
                "androidx.lifecycle:lifecycle-runtime:2.6.1",
                "androidx.activity:activity-ktx:1.7.0",
                "androidx.compose.animation:animation-core:1.2.1",
                "androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1",
            ]
        }

        onContext<WorkspaceContext>(
            id = RootModule.ID_WORKSPACE,
            checkpoint = AndroidToolsFeature.CHECKPOINT_BEFORE_JVM_EXTERNAL
        ) {
            load("//third_party:maven_dependencies.bzl", "FORCED_MAVEN_DEPENDENCIES")
        }

        onContext<MavenInstallContext>(id = AndroidToolsFeature.ID_MAVEN_INSTALL) {
            artifacts = ListReference<StringType>("FORCED_MAVEN_DEPENDENCIES")
        }
    }
}