/*
 * Copyright 2021 Pavlo Stavytskyi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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