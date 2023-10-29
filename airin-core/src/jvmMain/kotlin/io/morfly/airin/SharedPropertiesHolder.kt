package io.morfly.airin

interface SharedPropertiesHolder {

    val sharedProperties: MutableMap<String, Any?>

    val sharedPropertiesAvailable: Boolean
}
