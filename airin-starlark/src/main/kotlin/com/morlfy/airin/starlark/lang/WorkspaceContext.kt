package com.morlfy.airin.starlark.lang

import com.morlfy.airin.starlark.elements.WorkspaceFile


/**
 *
 */
class WorkspaceContext : StarlarkContext()

/**
 *
 */
inline fun WORKSPACE(body: WorkspaceContext.() -> Unit): WorkspaceFile =
    WorkspaceContext()
        .apply(body)
        .let { WorkspaceFile(hasExtension = false, statements = it.statements.toList()) }

/**
 *
 */
object WORKSPACE

/**
 *
 */
inline fun WORKSPACE.bazel(body: WorkspaceContext.() -> Unit): WorkspaceFile =
    WorkspaceContext()
        .apply(body)
        .let { WorkspaceFile(hasExtension = true, statements = it.statements.toList()) }