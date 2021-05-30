plugins {
    id("java-gradle-plugin")
    kotlin("jvm")
}

gradlePlugin {
    plugins {
        create("airin") {
            id = "com.morfly.airin"
            implementationClass = "com.morfly.airin.plugin.AirinGradlePlugin"
        }
    }
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":airin-starlark"))
    implementation(project(":airin-migration:core"))
//    implementation("com.android.tools.build:gradle:4.2.1")
    implementation("com.android.tools.build:gradle:4.1.0")
}