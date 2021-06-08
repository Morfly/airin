package org.morfly.airin.sample.data

import androidx.compose.runtime.compositionLocalOf
import org.morfly.airin.sample.core.ImagesRepository


interface DataProvider {

    val imagesRepository: ImagesRepository
}

val LocalDataProvider = compositionLocalOf<DataProvider> { error("No data provider found!") }