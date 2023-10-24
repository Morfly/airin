//package io.morfly.airin
//
//import io.morfly.airin.label.Label
//import io.morfly.airin.label.MavenCoordinates
//import io.morfly.pendant.starlark.lang.context.Context
//
//
//abstract class ModificationComponent<P : PackageDescriptor, S> : Component<P>() {
//    protected abstract val subject: S
//    protected abstract val onInvoke: S.(P) -> Unit
//
//    override val id: String = javaClass.simpleName
//
//    override fun invoke(packageDescriptor: P) {
//        onInvoke(packageDescriptor)
//    }
//
//    override fun onInvoke(packageDescriptor: P) {
//        subject.onInvoke(packageDescriptor)
//    }
//}
//
//
//class CodeModification<P : PackageDescriptor, C : Context>(
//    override val subject: C,
//    override val onInvoke: C.(P) -> Unit
//) : ModificationComponent<P, C>()
//
//class ArtifactOverrideModification<P : PackageDescriptor>(
//    override val subject: MavenCoordinates,
//    override val onInvoke: MavenCoordinates.(P) -> Unit
//) : ModificationComponent<P, MavenCoordinates>()
//
//class PackageFilterModification<P : PackageDescriptor, L : Label>(
//    override val subject: L,
//    override val onInvoke: L.(P) -> Unit
//) : ModificationComponent<P, L>()
