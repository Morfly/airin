//package com.morfly.airin.plugin.task
//
//import com.morfly.airin.GradleMigratorToBazel
//import org.gradle.api.DefaultTask
//import org.gradle.api.tasks.TaskAction
//
//
//open class MigrateToBazelTask : DefaultTask() {
//
//    private val migrator = GradleMigratorToBazel(project)
//
//    @TaskAction
//    fun migrateToBazel() {
//        println("starting migration...")
//        migrator.migrate()
//    }
//
//    companion object {
//        const val NAME = "migrateToBazel"
//    }
//}