package com.morfly.airin.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project


class AirinGradlePlugin : Plugin<Project> {

    override fun apply(target: Project) {
//        with(target.tasks) {
//            register(MigrateToBazelTask.NAME, MigrateToBazelTask::class.java)
//        }
//
//        with(target.extensions) {
//            create(AirinExtension.NAME, AirinExtension::class.java, target.objects)
//        }
    }
}