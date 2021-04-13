package com.morfly.airin.task

import com.morlfy.airin.starlark.TestContext
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction


open class GenerateBazelFilesTask : DefaultTask() {

    @TaskAction
    fun generateBazelFiles() {
        TestContext()
        println("migrate to bazel")
    }

    companion object {
        const val NAME = "generateBazelFiles"
    }
}