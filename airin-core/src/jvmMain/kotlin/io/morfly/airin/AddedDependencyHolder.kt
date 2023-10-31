package io.morfly.airin

import io.morfly.airin.label.Label

interface AddedDependencyHolder {

    val addedDependencies: MutableMap<ConfigurationName, MutableSet<Label>>

    fun addDependency(label: Label, configuration: String) {
        addedDependencies.getOrPut(configuration, ::mutableSetOf) += label
    }
}