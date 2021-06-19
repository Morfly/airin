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

@file:SuppressLint("ComposableNaming")

package org.morfly.airin.sample.profile.impl

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import org.morfly.airin.sample.core.FeatureEntries
import org.morfly.airin.sample.core.di.lib.injectedViewModel
import org.morfly.airin.sample.data.LocalDataProvider
import org.morfly.airin.sample.profile.ProfileEntry
import org.morfly.airin.sample.profile.impl.di.DaggerProfileComponent
import org.morfly.airin.sample.profile.impl.ui.ProfileScreen
import javax.inject.Inject


class ProfileEntryImpl @Inject constructor() : ProfileEntry() {

    @Composable
    override fun invoke(navController: NavController, destinations: FeatureEntries, args: Bundle?) {
        val viewModel = injectedViewModel {
            DaggerProfileComponent.factory().create(
                dataProvider = LocalDataProvider.current,
                userId = args!!.getLong(USER_ID_ARG)
            ).viewModel
        }

        ProfileScreen(viewModel)
    }
}