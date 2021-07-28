package org.morfly.sample.imagedata.di

import dagger.Binds
import dagger.Module
import org.morfly.sample.imagedata.DefaultImagesRepository
import org.morfly.sample.imagedata.ImagesRepository


@Module
interface ImageDataModule {

    @Binds
    fun bind(impl: DefaultImagesRepository): ImagesRepository
}