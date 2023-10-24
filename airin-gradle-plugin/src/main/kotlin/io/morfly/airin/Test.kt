package io.morfly.airin

import io.morfly.pendant.starlark.lang.context.BUILD
import io.morfly.pendant.starlark.lang.context.BuildContext
import io.morfly.pendant.starlark.lang.context.bazel
import io.morfly.pendant.starlark.lang.onContext
import org.gradle.api.Project


abstract class AndroidLibraryModule : GradlePackageComponent() {

    override fun canProcess(target: Project): Boolean {
        return target.name == "navigation"
    }

    override fun PackageContext.onInvoke(packageDescriptor: GradleProject) {
        val buildFile = BUILD.bazel {
            _id = "test"
            load("@test", "test")
        }

        generate(buildFile)
    }
}

abstract class JetpackComposeFeature : GradleFeatureComponent() {

    override fun canProcess(target: Project): Boolean {
        return true
    }

    override fun FeatureContext.onInvoke(packageDescriptor: GradleProject) {
        onContext<BuildContext>("test") {
            load("@workspace", "test")

            val S by "1"

            S `=` "s"

            "test" {
                "name" `=` "value"
            }
        }
    }
}

abstract class ArtifactOverridesFeature : GradleFeatureComponent() {

    override fun canProcess(target: Project): Boolean {
        return true
    }

    override fun FeatureContext.onInvoke(packageDescriptor: GradleProject) {
    }
}
