package io.morfly.airin.plugin

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Project
import com.android.build.api.dsl.CommonExtension

class AirinAndroidGradlePlugin : AirinGradlePlugin()

val Project.isComposeEnabled: Boolean
    get() = extensions.findByType(CommonExtension::class.java)?.buildFeatures?.compose ?: false

val Project.applicationId: String?
    get() = extensions.findByType(ApplicationExtension::class.java)?.defaultConfig?.applicationId

