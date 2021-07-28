plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    google()
}

dependencies {
//    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    val airinVersion = "0.3.0"
    implementation("org.morfly.airin:airin-gradle:$airinVersion")
    implementation("org.morfly.airin:airin-gradle-android:$airinVersion")

    implementation("com.android.tools.build:gradle:4.2.2")
}