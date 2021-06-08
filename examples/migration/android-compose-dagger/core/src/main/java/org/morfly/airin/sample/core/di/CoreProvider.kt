package org.morfly.airin.sample.core.di

import android.content.Context
import androidx.compose.runtime.compositionLocalOf
import kotlinx.coroutines.CoroutineDispatcher


interface CoreProvider {

    val context: Context

    val backgroundDispatcher: CoroutineDispatcher
}

val LocalCoreProvider = compositionLocalOf<CoreProvider> { error("No core provider found!") }