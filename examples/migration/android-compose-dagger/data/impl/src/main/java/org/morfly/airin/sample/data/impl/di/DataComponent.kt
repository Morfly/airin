package org.morfly.airin.sample.data.impl.di

import dagger.Component
import org.morfly.airin.sample.core.di.CoreProvider
import org.morfly.airin.sample.data.DataProvider
import javax.inject.Singleton


@Singleton
@Component(
    dependencies = [CoreProvider::class],
    modules = [DataModule::class]
)
interface DataComponent : DataProvider