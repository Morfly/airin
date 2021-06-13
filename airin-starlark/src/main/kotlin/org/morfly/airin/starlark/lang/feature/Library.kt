package org.morfly.airin.starlark.lang.feature

import org.morfly.airin.starlark.lang.api.StatementsHolder


interface GlobalLibrary

interface BazelLibrary : StatementsHolder

interface BuildLibrary : BazelLibrary, StatementsHolder

interface WorkspaceLibrary : BazelLibrary, StatementsHolder