import org.gradle.api.Project
import org.morfly.airin.GradleStandaloneTemplateProvider
import org.morfly.airin.starlark.elements.StarlarkFile
import template.workspace_template


class Workspace : GradleStandaloneTemplateProvider() {

    override fun provide(target: Project, relativePath: String): List<StarlarkFile> =
        listOf(
            workspace_template(
                name = target.rootProject.name,
                artifactDeps = sharedData.allArtifacts.asString(version = true)
            )
        )
}
