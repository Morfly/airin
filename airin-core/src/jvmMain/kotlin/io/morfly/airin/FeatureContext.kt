package io.morfly.airin

import io.morfly.airin.label.BazelLabel
import io.morfly.airin.label.Label
import io.morfly.airin.label.MavenCoordinates
import io.morfly.pendant.starlark.lang.Checkpoint
import io.morfly.pendant.starlark.lang.ContextId
import io.morfly.pendant.starlark.lang.Modifier
import io.morfly.pendant.starlark.lang.ModifiersHolder

class FeatureContext : ModifiersHolder {

    override val modifiers =
        linkedMapOf<ContextId, MutableMap<Checkpoint, MutableList<Modifier<*>>>>()

    fun onConfiguration(configuraion: String, mapping: (String) -> String) {

    }

    fun onArtifact(group: String?, name: String, version: String?, override: MavenCoordinates.() -> Label?) {

    }

    fun test() {
        onArtifact("", "", "") { null }
    }
}
