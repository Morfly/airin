package org.morfly.sample

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import org.morfly.sample.imagedata.ImagesRepository
import org.morfly.sample.imagedata.di.ImageDataComponent
import javax.inject.Singleton


@Singleton
@Component(dependencies = [ImageDataComponent::class])
interface AppComponent {

    val context: Context

    val imagesRepository: ImagesRepository

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance context: Context,
            imageDataComponent: ImageDataComponent
        ): AppComponent
    }
}


interface AppComponentProvider {
    val appComponent: AppComponent
}