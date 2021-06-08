package org.morfly.airin.sample.core.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [CoroutinesModule::class])
interface CoreComponent : CoreProvider {

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance context: Context): CoreComponent
    }
}