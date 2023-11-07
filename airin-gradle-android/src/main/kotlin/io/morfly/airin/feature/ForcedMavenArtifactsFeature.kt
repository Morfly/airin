package io.morfly.airin.feature

import io.morfly.airin.FeatureContext
import io.morfly.airin.GradleFeatureComponent
import io.morfly.airin.GradleModule
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

    override fun FeatureContext.onInvoke(packageDescriptor: GradleModule) {
        if (artifacts.isEmpty()) return

        onContext<BzlContext>(id = RootModule.ID_MAVEN_DEPENDENCIES_BZL) {

            val FORCED_MAVEN_ARTIFACTS by artifacts
        }

        onContext<WorkspaceContext>(
            id = RootModule.ID_WORKSPACE,
            checkpoint = AndroidToolsFeature.CHECKPOINT_BEFORE_JVM_EXTERNAL
        ) {
            load("//third_party:maven_dependencies.bzl", "FORCED_MAVEN_ARTIFACTS")
        }

        onContext<MavenInstallContext>(id = AndroidToolsFeature.ID_MAVEN_INSTALL) {
            artifacts = ListReference<StringType>("FORCED_MAVEN_ARTIFACTS")
        }
    }
}