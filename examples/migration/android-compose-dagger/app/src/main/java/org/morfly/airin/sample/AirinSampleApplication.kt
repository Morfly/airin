package org.morfly.airin.sample

import android.app.Application
import org.morfly.airin.sample.core.di.DaggerCoreComponent
import org.morfly.airin.sample.data.impl.di.DaggerDataComponent
import org.morfly.airin.sample.di.AppProvider
import org.morfly.airin.sample.di.DaggerAppComponent
import org.morfly.airin.sample.imagelist.impl.di.DaggerImageListEntryComponent
import org.morfly.airin.sample.profile.impl.di.DaggerProfileEntryComponent


class AirinSampleApplication : Application() {

    lateinit var appProvider: AppProvider

    override fun onCreate() {
        super.onCreate()

        val coreProvider = DaggerCoreComponent.factory().create(this)
        appProvider = DaggerAppComponent.builder()
            .coreProvider(coreProvider)
            .dataProvider(DaggerDataComponent.builder().coreProvider(coreProvider).build())
            .imageListEntryProvider(DaggerImageListEntryComponent.create())
            .profileEntryProvider(DaggerProfileEntryComponent.create())
            .build()
    }
}