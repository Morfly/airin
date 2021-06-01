plugins {
    kotlin("jvm")
}


dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":airin-gradle"))

    implementation("com.android.tools.build:gradle:4.1.0")
}