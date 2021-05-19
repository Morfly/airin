package com.morfly.airin.template.java

import org.morfly.bazelgen.generator.dsl.WORKSPACE
import org.morfly.bazelgen.generator.dsl.function.workspace


/**
 *
 */
fun java_workspace_file(
    workspaceName: String? = null
    /**
     *
     */
) = WORKSPACE {
    if (workspaceName != null) workspace(name = workspaceName)

}