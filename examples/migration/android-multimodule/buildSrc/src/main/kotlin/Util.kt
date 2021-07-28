import org.morfly.airin.MavenArtifact


fun Collection<MavenArtifact>.asString(version: Boolean): List<String> =
    map { artifact -> artifact.toString(version) }