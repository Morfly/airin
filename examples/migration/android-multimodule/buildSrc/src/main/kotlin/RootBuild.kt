import org.gradle.api.Project
import org.morfly.airin.GradleStandaloneTemplateProvider
import org.morfly.airin.starlark.elements.StarlarkFile
import template.root_build_template


class RootBuild: GradleStandaloneTemplateProvider() {

    override fun provide(target: Project, relativePath: String): List<StarlarkFile> =
        listOf(root_build_template())
}