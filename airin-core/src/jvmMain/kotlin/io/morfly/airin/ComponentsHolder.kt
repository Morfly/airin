package io.morfly.airin

interface ComponentsHolder<P : Module> {

    val subcomponents: MutableMap<ComponentId, Component<P>>
}
