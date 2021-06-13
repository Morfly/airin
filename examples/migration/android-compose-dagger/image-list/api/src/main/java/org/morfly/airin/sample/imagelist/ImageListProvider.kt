package org.morfly.airin.sample.imagelist

import androidx.compose.runtime.compositionLocalOf


interface ImageListProvider


val LocalImageListProvider = compositionLocalOf<ImageListProvider> {
    error("No image list provider found!")
}