package org.morfly.airin.sample.imagelist.impl.di

import dagger.Binds
import dagger.Module
import org.morfly.airin.sample.imagelist.ImageListEntry
import org.morfly.airin.sample.imagelist.impl.ImageListEntryImpl


@Module
interface ImageListEntryModule {

    @Binds
    fun imageListEntry(impl: ImageListEntryImpl): ImageListEntry

//    @Binds
//    @IntoMap
//    @ClassKey(ImageListEntry::class)
//    fun imageListEntry1(impl: ImageListScreen): ImageListEntry

//    @Provides
//    @IntoMap
//    @StringKey("hello")
//    fun provideStr(): String {
//        return "hello"
//    }
}