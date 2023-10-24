package io.morfly.airin

import java.io.Serializable

enum class MissingComponentResolution : Serializable {
    Fail,
    Ignore
}

enum class ComponentConflictResolution : Serializable {
    Fail,
    UsePriority,
    Ignore
}
