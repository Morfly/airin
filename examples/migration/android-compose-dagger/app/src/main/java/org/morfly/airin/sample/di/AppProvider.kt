package org.morfly.airin.sample.di

import androidx.compose.runtime.compositionLocalOf
import org.morfly.airin.sample.core.FeatureEntries
import org.morfly.airin.sample.core.di.CoreProvider
import org.morfly.airin.sample.data.DataProvider
import org.morfly.airin.sample.imagelist.ImageListEntryProvider


interface AppProvider : CoreProvider, DataProvider, ImageListEntryProvider {

    val destinations: FeatureEntries
}

val LocalAppProvider = compositionLocalOf<AppProvider> { error("No app provider found!") }