package io.morfly.airin

class ArtifactContext(val artifacts: Set<String>) {

    private val overrides = mutableSetOf<String>()
    fun overrideWith(label: String) {
        overrides += overrides
    }
}

//    fun <C : FunctionCallContext> onFunctionCall(
//        id: String,
//        includeShared: Boolean = false,
//        config: C.() -> Unit
//    ) {
//        val modification = CodeModification<T>(FunctionCallContext(), {})
//    }
//
//    fun <C : CommonStarlarkContext<C>> onFile(
//        includeShared: Boolean = false,
//        config: C.() -> Unit
//    ) {
//
//    }
//
//    fun onArtifact(artifact: String, overrideWith: (ArtifactContext.() -> Unit)?) {
//        val context = ArtifactContext(setOf(artifact))
//        overrideWith?.invoke(context)
//    }
//
//    fun onArtifacts(vararg artifact: String, overrideWith: (ArtifactContext.() -> Unit)?) {
//        val context = ArtifactContext(artifact.toSet())
//        overrideWith?.invoke(context)
//    }
//
//    fun test() {
//        onArtifact("io.morfly.airin:airin") {
//            overrideWith("//airin")
//        }
//
//        onArtifact("io.morfly.airin:airin", overrideWith = null)
//
//        onArtifacts(
//            "io.morfly.airin:airin",
//            "io.morfly.airin:airin"
//        ) {
//            overrideWith("")
//        }
//    }
