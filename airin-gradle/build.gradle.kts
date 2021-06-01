plugins {
    id("java-gradle-plugin")
    kotlin("jvm")
}

gradlePlugin {
    plugins {
        create("airin") {
            id = "org.morfly.airin"
            implementationClass = "org.morfly.airin.plugin.AirinGradlePlugin"
        }
    }
}

dependencies {
    implementation(kotlin("stdlib"))
    api(project(":airin-starlark"))
    api(project(":airin-migration-core"))
//    implementation("com.android.tools.build:gradle:4.1.0")
}