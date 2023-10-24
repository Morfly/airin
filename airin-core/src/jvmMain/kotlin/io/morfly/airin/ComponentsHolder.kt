package io.morfly.airin

interface ComponentsHolder<P : PackageDescriptor> {

    val subcomponents: MutableList<Component<P>>
}
