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