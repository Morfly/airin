package org.morfly.airin.sample.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import org.morfly.airin.sample.core.FeatureEntry
import org.morfly.airin.sample.core.di.lib.FeatureEntryKey
import org.morfly.airin.sample.imagelist.ImageListEntry


@Module
interface NavigationModule {

    @Binds
    @IntoMap
    @FeatureEntryKey(ImageListEntry::class)
    fun imageListEntry(entry: ImageListEntry): FeatureEntry
}