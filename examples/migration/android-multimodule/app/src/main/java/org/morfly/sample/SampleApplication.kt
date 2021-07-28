package org.morfly.sample

import android.app.Application
import org.morfly.sample.imagedata.di.DaggerImageDataComponent


class SampleApplication : Application(), AppComponentProvider {

    override lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.factory()
            .create(this, DaggerImageDataComponent.builder().build())
    }
}