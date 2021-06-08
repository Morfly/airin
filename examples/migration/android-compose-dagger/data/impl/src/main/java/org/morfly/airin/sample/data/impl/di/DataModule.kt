package org.morfly.airin.sample.data.impl.di

import dagger.Binds
import dagger.Module
import org.morfly.airin.sample.core.ImagesRepository
import org.morfly.airin.sample.data.impl.DefaultImagesRepository
import org.morfly.airin.sample.data.impl.mapping.DataMapper
import org.morfly.airin.sample.data.impl.mapping.DefaultDataMapper


@Module(includes = [NetworkModule::class, StorageModule::class])
interface DataModule {

    @Binds
    fun mapper(impl: DefaultDataMapper): DataMapper

    @Binds
    fun imagesRepository(impl: DefaultImagesRepository): ImagesRepository
}