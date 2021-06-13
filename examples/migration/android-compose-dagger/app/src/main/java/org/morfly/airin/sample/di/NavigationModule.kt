package org.morfly.airin.sample.di

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import org.morfly.airin.sample.core.FeatureEntry
import org.morfly.airin.sample.core.di.lib.FeatureEntryKey
import org.morfly.airin.sample.imagelist.ImageListEntry
import org.morfly.airin.sample.profile.ProfileEntry


@Module
interface NavigationModule {

    @Binds
    @IntoMap
    @FeatureEntryKey(ImageListEntry::class)
    fun imageListEntry(entry: ImageListEntry): FeatureEntry

    @Binds
    @IntoMap
    @FeatureEntryKey(ProfileEntry::class)
    fun profileEntry(entry: ProfileEntry): FeatureEntry
}