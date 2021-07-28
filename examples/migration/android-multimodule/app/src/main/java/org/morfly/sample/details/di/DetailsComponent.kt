package org.morfly.sample.details.di

import dagger.BindsInstance
import dagger.Component
import org.morfly.sample.AppComponent
import org.morfly.sample.details.DetailsFragment
import org.morfly.sample.details.DetailsViewModel
import org.morfly.sample.imagedata.Image


@DetailsScoped
@Component(dependencies = [AppComponent::class])
interface DetailsComponent {

    val detailsViewModel: DetailsViewModel

    fun inject(fragment: DetailsFragment)


    @Component.Factory
    interface Factory {

        fun create(
            appComponent: AppComponent,
            @BindsInstance image: Image
        ): DetailsComponent
    }
}