package io.morfly.airin

interface ComponentsHolder<P : PackageDescriptor> {

    val subcomponents: MutableMap<ComponentId, Component<P>>
}
