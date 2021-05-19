package com.morfly.airin

import com.morfly.airin.plugin.AirinGradlePlugin
import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldNotBe
import org.gradle.testfixtures.ProjectBuilder


class AirinGradlePluginTest : WordSpec({

    "Using the Plugin ID" should {
        "Apply the Plugin" {
            val project = ProjectBuilder.builder().build()
            project.pluginManager.apply("com.morfly.airin")

            project.plugins.getPlugin(AirinGradlePlugin::class.java) shouldNotBe null
        }
    }
})