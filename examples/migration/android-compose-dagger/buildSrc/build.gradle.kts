plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    google()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$embeddedKotlinVersion")
    implementation("com.android.tools.build:gradle:7.0.0-beta03")
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
//    implementation("org.morfly.airin:airin-starlark:0.1.0")
}