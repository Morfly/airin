package io.morfly.airin

import io.morfly.airin.label.Label

typealias AddedDependencyCollection = MutableMap<ConfigurationName, MutableSet<Label>>

interface AddedDependencyHolder {

    val addedDependencies: AddedDependencyCollection

    fun addDependency(label: Label, configuration: String) {
        addedDependencies.getOrPut(configuration, ::mutableSetOf) += label
    }
}