@file:Suppress("PropertyName")

package org.morfly.airin.starlark.lang.feature

import org.morfly.airin.starlark.lang.api.LanguageFeature


internal interface BooleanValuesFeature : LanguageFeature {

    val True get() = true

    val False get() = false
}