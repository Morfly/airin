package org.morfly.airin.sample.di

import dagger.Component
import org.morfly.airin.sample.AirinSampleApplication
import org.morfly.airin.sample.core.di.CoreProvider
import org.morfly.airin.sample.data.DataProvider
import org.morfly.airin.sample.imagelist.ImageListEntryProvider
import javax.inject.Singleton


@Singleton
@Component(
    dependencies = [CoreProvider::class, DataProvider::class, ImageListEntryProvider::class],
    modules = [NavigationModule::class]
)
interface AppComponent : AppProvider {

    fun inject(application: AirinSampleApplication)
}