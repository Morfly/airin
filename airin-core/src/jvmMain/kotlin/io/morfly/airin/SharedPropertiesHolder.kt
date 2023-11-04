package io.morfly.airin

// TODO delete
interface SharedPropertiesHolder {

    val sharedProperties: MutableMap<String, Any?>

    val sharedPropertiesAvailable: Boolean
}
