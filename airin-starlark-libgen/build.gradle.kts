plugins {
    kotlin("jvm")
}

dependencies {
    implementation(project(":airin-starlark"))

    implementation(kotlin("stdlib"))
    implementation(deps.ksp)
}