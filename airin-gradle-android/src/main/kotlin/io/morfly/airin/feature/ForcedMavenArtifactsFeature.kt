package io.morfly.airin.feature

import io.morfly.airin.FeatureContext
import io.morfly.airin.GradleFeatureComponent
import io.morfly.airin.GradleProject
import io.morfly.airin.module.RootModule
import io.morfly.airin.property
import io.morfly.pendant.starlark.MavenInstallContext
import io.morfly.pendant.starlark.element.ListReference
import io.morfly.pendant.starlark.lang.context.BzlContext
import io.morfly.pendant.starlark.lang.context.WorkspaceContext
import io.morfly.pendant.starlark.lang.onContext
import io.morfly.pendant.starlark.lang.type.StringType
import org.gradle.api.Project

abstract class ForcedMavenArtifactsFeature : GradleFeatureComponent() {

    var artifacts by property(emptyList<String>())

    override fun canProcess(target: Project) = true

    override fun FeatureContext.onInvoke(packageDescriptor: GradleProject) {
        if (artifacts.isEmpty()) return

        onContext<BzlContext>(id = RootModule.ID_MAVEN_DEPENDENCIES_BZL) {

            val FORCED_MAVEN_DEPENDENCIES by artifacts
            list[
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