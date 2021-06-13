package org.morfly.airin.sample.di

import androidx.compose.runtime.compositionLocalOf
import dagger.Component
import org.morfly.airin.sample.AirinSampleApplication
import org.morfly.airin.sample.core.FeatureEntries
import org.morfly.airin.sample.core.di.CoreProvider
import org.morfly.airin.sample.data.DataProvider
import org.morfly.airin.sample.imagelist.ImageListEntryProvider
import org.morfly.airin.sample.profile.ProfileEntryProvider
import javax.inject.Singleton


@Singleton
@Component(
    dependencies = [
        CoreProvider::class,
        DataProvider::class,
        ImageListEntryProvider::class,
        ProfileEntryProvider::class
    ],
    modules = [NavigationModule::class]
)
interface AppComponent : AppProvider {

    fun inject(application: AirinSampleApplication)
}


interface AppProvider :
    CoreProvider,
    DataProvider,
    ImageListEntryProvider,
    ProfileEntryProvider {

    val destinations: FeatureEntries
}


val LocalAppProvider = compositionLocalOf<AppProvider> { error("No app provider found!") }