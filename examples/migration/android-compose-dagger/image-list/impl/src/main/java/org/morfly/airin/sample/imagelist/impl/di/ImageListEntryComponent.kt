package org.morfly.airin.sample.imagelist.impl.di

import dagger.Component
import org.morfly.airin.sample.imagelist.ImageListEntryProvider


@Component(modules = [ImageListEntryModule::class])
interface ImageListEntryComponent : ImageListEntryProvider