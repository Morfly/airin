package org.morfly.airin.sample.core.di

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton


@Module
object CoroutinesModule {

    @Provides
    @Singleton
    fun provideBackgroundDispatcher(): CoroutineDispatcher =
        Dispatchers.Default
}