plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    google()
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.10")
    implementation("com.android.tools.build:gradle:7.1.0-alpha02")
//    implementation("org.morfly.airin:airin-starlark:0.1.0")
}