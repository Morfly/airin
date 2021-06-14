plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    google()
}

dependencies {
//    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    val airinVersion = "0.2.0"
    implementation("org.morfly.airin:airin-gradle:$airinVersion")
    implementation("org.morfly.airin:airin-gradle-android:$airinVersion")

    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.10")
    implementation("com.android.tools.build:gradle:7.1.0-alpha02")
}