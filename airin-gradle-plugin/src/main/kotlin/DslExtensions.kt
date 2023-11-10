import io.morfly.airin.FeatureComponent
import io.morfly.airin.GradleModuleDecorator
import io.morfly.airin.ModuleComponent
import io.morfly.airin.dsl.AirinExtension
import io.morfly.airin.dsl.FeatureComponentsHolder
import io.morfly.airin.dsl.PackageComponentsHolder
import org.gradle.api.Action

inline fun <reified C : ModuleComponent> PackageComponentsHolder.register(config: Action<C>? = null) =
    register(C::class.java, config)

inline fun <reified C : FeatureComponent> FeatureComponentsHolder.include(config: Action<C>? = null) =
    include(C::class.java, config)

inline fun <reified D : GradleModuleDecorator> AirinExtension.decorateWith() {
    decorateWith(D::class.java)
}
