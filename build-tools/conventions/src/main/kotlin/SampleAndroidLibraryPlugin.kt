import io.morfly.airin.buildtools.AirinConventionPlugin
import io.morfly.airin.buildtools.CommonExtension
import io.morfly.airin.buildtools.android
import io.morfly.airin.buildtools.sampleLibs

class SampleAndroidLibraryPlugin : AirinConventionPlugin({
    with(pluginManager) {
        apply(sampleLibs.plugins.android.library.get().pluginId)
        apply(sampleLibs.plugins.sample.android.common.get().pluginId)
    }

    android<CommonExtension> {
        buildTypes {
            getByName("release") {
                isMinifyEnabled = true
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
        }
    }
})