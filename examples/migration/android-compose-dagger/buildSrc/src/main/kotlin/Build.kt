import org.gradle.api.Project
import org.morfly.airin.GradlePerModuleTemplateProvider
import org.morfly.airin.starlark.elements.StarlarkFile

class Build : GradlePerModuleTemplateProvider() {

    override fun canProvide(target: Project): Boolean {
        TODO("Not yet implemented")
    }

    override fun provide(target: Project, relativePath: String): List<StarlarkFile> {
        TODO("Not yet implemented")
    }

}