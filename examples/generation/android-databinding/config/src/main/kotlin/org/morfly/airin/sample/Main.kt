package org.morfly.airin.sample

import org.morfly.airin.sample.generator.ProjectGenerator
import kotlin.system.measureTimeMillis


fun main() {
    val projectGenerator = ProjectGenerator()

    println("Generating project...")

    val millis = measureTimeMillis {
        projectGenerator.generate(numOfModules = 4, depsOverlap = 2)
    }

    println("Project successfully generated in ${millis / 1000.0} seconds.")
}