package com.morfly.airin.lib

import org.morfly.bazelgen.generator.file.BazelFile


interface MigratorToBazel {

    fun migrate()
}