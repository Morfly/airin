package io.morfly.airin.feature

import io.morfly.airin.FeatureContext
import io.morfly.airin.GradleFeatureComponent
import io.morfly.airin.GradleProject
import io.morfly.airin.module.RootModule
import io.morfly.airin.property
import io.morfly.pendant.starlark.MavenInstallContext
import io.morfly.pendant.starlark.dagger_android_rules
import io.morfly.pendant.starlark.element.ListReference
import io.morfly.pendant.starlark.http_archive
import io.morfly.pendant.starlark.lang.context.BuildContext
import io.morfly.pendant.starlark.lang.context.WorkspaceContext
import io.morfly.pendant.starlark.lang.onContext
import io.morfly.pendant.starlark.lang.type.StringType
import org.gradle.api.Project

abstract class DaggerAndroidFeature : GradleFeatureComponent() {

    val daggerVersion by property("2.47")
    val daggerSha by property("154cdfa4f6f552a9873e2b4448f7a80415cb3427c4c771a50c6a8a8b434ffd0a")

    override fun canProcess(target: Project): Boolean = true

    override fun FeatureContext.onInvoke(packageDescriptor: GradleProject) {

        onContext<WorkspaceContext>(
            id = RootModule.ID_WORKSPACE,
            checkpoint = AndroidToolsFeature.CHECKPOINT_BEFORE_JVM_EXTERNAL
        ) {
            val DAGGER_VERSION by daggerVersion
            val DAGGER_SHA by daggerSha

            http_archive(
                name = "dagger",
                sha256 = DAGGER_SHA,
                strip_prefix = "dagger-dagger-%s" `%` DAGGER_VERSION,
                urls = list["https://github.com/google/dagger/archive/dagger-%s.zip" `%` DAGGER_VERSION],
            )

            load("@dagger//:workspace_defs.bzl", "DAGGER_ANDROID_ARTIFACTS", "DAGGER_ANDROID_REPOSITORIES")
        }

        onContext<BuildContext>(id = RootModule.ID_BUILD) {
            load("@dagger//:workspace_defs.bzl", "dagger_android_rules")

            dagger_android_rules()
        }

        onContext<MavenInstallContext>(id = AndroidToolsFeature.ID_MAVEN_INSTALL) {
            artifacts = ListReference<StringType>("DAGGER_ANDROID_ARTIFACTS")
            repositories = ListReference<StringType>("DAGGER_ANDROID_REPOSITORIES")
        }
    }
}
