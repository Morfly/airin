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