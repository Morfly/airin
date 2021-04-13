package com.morfly.airin

import com.morfly.airin.task.GenerateBazelFilesTask
import org.gradle.api.Plugin
import org.gradle.api.Project


class AirinGradlePlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target.tasks) {
            register(GenerateBazelFilesTask.NAME, GenerateBazelFilesTask::class.java)
        }
    }
}