import io.morfly.airin.feature.AndroidToolsFeature
import io.morfly.airin.feature.JetpackComposeFeature
import io.morfly.airin.module.RootModule

plugins {
    id("io.morfly.airin.android") version "0.5.0"
}

airin {
    register<RootModule> {
        include<AndroidToolsFeature>()
        include<JetpackComposeFeature>()
    }
}