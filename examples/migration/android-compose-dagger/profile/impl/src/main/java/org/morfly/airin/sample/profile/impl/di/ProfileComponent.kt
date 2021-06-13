package org.morfly.airin.sample.profile.impl.di

import dagger.BindsInstance
import dagger.Component
import org.morfly.airin.sample.core.di.lib.ScreenScoped
import org.morfly.airin.sample.data.DataProvider
import org.morfly.airin.sample.profile.impl.ProfileViewModel

@ScreenScoped
@Component(dependencies = [DataProvider::class])
interface ProfileComponent {

    val viewModel: ProfileViewModel

    @Component.Factory
    interface Factory {

        fun create(
            dataProvider: DataProvider,
            @BindsInstance @UserId userId: Long
        ): ProfileComponent
    }
}