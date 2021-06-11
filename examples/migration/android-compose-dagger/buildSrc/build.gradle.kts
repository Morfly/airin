plugins {
    kotlin("jvm") version "1.5.10"
}

repositories {
    mavenCentral()
    google()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.5.10")
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
//    implementation("org.morfly.airin:airin-starlark:0.1.0")
}