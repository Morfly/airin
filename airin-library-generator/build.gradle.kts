plugins {
    kotlin("jvm")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":airin-starlark"))

    implementation(deps.ksp)
}