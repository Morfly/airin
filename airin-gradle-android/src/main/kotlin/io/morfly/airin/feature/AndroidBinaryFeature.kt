package io.morfly.airin.feature

import io.morfly.airin.FeatureContext
import io.morfly.airin.FeatureComponent
import io.morfly.airin.GradleModule
import io.morfly.airin.androidMetadata
import io.morfly.airin.module.AndroidLibraryModule
import io.morfly.pendant.starlark.android_binary
import io.morfly.pendant.starlark.lang.context.BuildContext
import io.morfly.pendant.starlark.lang.onContext
import org.gradle.api.Project

abstract class AndroidBinaryFeature : FeatureComponent() {

    override fun canProcess(project: Project): Boolean =
        project.plugins.hasPlugin("com.android.application")

    override fun FeatureContext.onInvoke(module: GradleModule) {
        onContext<BuildContext>(AndroidLibraryModule.ID_BUILD) {
            android_binary {
                name = "${module.name}_bin"
                incremental_dexing = 1
                manifest = "src/main/AndroidManifest.xml"
                manifest_values = dict {
                    val data = module.androidMetadata

                    "applicationId" to data?.applicationId
                    "minSdkVersion" to data?.minSdkVersion?.toString()
                    "targetSdkVersion" to data?.targetSdkVersion?.toString()
                    "compileSdkVersion" to data?.compileSdkVersion?.toString()
                    "versionCode" to data?.versionCode?.toString()
                    "versionName" to data?.versionName
                }
                multidex = "native"
                deps = list[":${module.name}"]
            }
        }
    }
}