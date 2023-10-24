package io.morfly.airin.buildtools

import com.vanniktech.maven.publish.MavenPublishBaseExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

// Makes 'libs' version catalog available for precompiled plugins in a type-safe manner.
// https://github.com/gradle/gradle/issues/15383#issuecomment-1245546796
val Project.libs get() = extensions.getByType<LibrariesForLibs>()

fun Project.kotlin(body: KotlinMultiplatformExtension.() -> Unit): Unit =
    extensions.configure("kotlin", body)

fun Project.mavenPublishing(configure: MavenPublishBaseExtension.() -> Unit) {
    extensions.configure("mavenPublishing", configure)
}

fun KotlinMultiplatformExtension.sourceSets(configure: NamedDomainObjectContainer<KotlinSourceSet>.() -> Unit) {
    (this as ExtensionAware).extensions.configure("sourceSets", configure)
}
