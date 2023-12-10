import io.morfly.airin.buildtools.AirinConventionPlugin
import io.morfly.airin.buildtools.libs

// TODO
class AirinGradlePublishPlugin: AirinConventionPlugin({
    with(pluginManager) {
        apply(libs.plugins.vanniktech.maven.publish.get().pluginId)
        apply(libs.plugins.dokka.get().pluginId)
    }
})