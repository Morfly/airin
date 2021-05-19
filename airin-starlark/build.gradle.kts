plugins {
    kotlin("jvm")
}

dependencies {
    implementation(kotlin("stdlib"))

    val kotestVersion: String by rootProject.extra
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
}