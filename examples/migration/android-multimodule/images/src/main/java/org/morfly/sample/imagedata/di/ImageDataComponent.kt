package org.morfly.sample.imagedata.di

import dagger.Component
import org.morfly.sample.imagedata.ImagesRepository


@Component(modules = [ImageDataModule::class])
interface ImageDataComponent {

    val imagesRepository: ImagesRepository
}