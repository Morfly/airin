import io.morfly.airin.buildtools.AirinConventionPlugin
import io.morfly.airin.buildtools.CommonExtension
import io.morfly.airin.buildtools.android
import io.morfly.airin.buildtools.debugImplementation
import io.morfly.airin.buildtools.implementation
import io.morfly.airin.buildtools.sampleLibs
import org.gradle.kotlin.dsl.dependencies

class SampleAndroidComposePlugin : AirinConventionPlugin({
    android<CommonExtension> {
        buildFeatures {
            compose = true
        }

        composeOptions {
            kotlinCompilerExtensionVersion = sampleLibs.versions.composeCompiler.get()
        }
    }

    dependencies {
        implementation(platform(sampleLibs.compose.bom))
        implementation(sampleLibs.compose.ui)
        implementation(sampleLibs.compose.ui.graphics)
        implementation(sampleLibs.compose.ui.tooling.preview)
        implementation(sampleLibs.compose.material3)
        debugImplementation(sampleLibs.compose.ui.tooling)
    }
})