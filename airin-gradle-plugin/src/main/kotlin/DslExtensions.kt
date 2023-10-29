import io.morfly.airin.GradleFeatureComponent
import io.morfly.airin.GradlePackageComponent
import io.morfly.airin.GradleProjectDecorator
import io.morfly.airin.dsl.AirinExtension
import io.morfly.airin.dsl.FeatureComponentsHolder
import io.morfly.airin.dsl.PackageComponentsHolder
import org.gradle.api.Action

inline fun <reified C : GradlePackageComponent> PackageComponentsHolder.register(config: Action<C>? = null) =
    register(C::class.java, config)

inline fun <reified C : GradleFeatureComponent> FeatureComponentsHolder.include(config: Action<C>? = null) =
    include(C::class.java, config)

inline fun <reified D: GradleProjectDecorator> AirinExtension.decorateWith() {
    projectDecorator = D::class.java
}
