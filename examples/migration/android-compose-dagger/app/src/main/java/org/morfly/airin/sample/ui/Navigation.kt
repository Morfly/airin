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

@file:Suppress("LocalVariableName")

package org.morfly.airin.sample.ui

import androidx.activity.OnBackPressedDispatcher
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.morfly.airin.sample.core.entry
import org.morfly.airin.sample.di.LocalAppProvider
import org.morfly.airin.sample.imagelist.ImageListEntry
import org.morfly.airin.sample.profile.ProfileEntry


@Composable
fun Navigation(onBackPressedDispatcher: OnBackPressedDispatcher) {
    val navController = rememberNavController()
    val destinations = LocalAppProvider.current.destinations

    val ImageListScreen = destinations.entry<ImageListEntry>()
    val ProfileScreen = destinations.entry<ProfileEntry>()

    Box(Modifier.fillMaxSize()) {
        NavHost(navController, startDestination = ImageListScreen.route) {
            composable(ImageListScreen.route, ImageListScreen.args) {
                ImageListScreen(navController, destinations, it.arguments)
            }
            composable(ProfileScreen.route, ProfileScreen.args) {
                ProfileScreen(navController, destinations, it.arguments)
            }
        }
        Box(Modifier.fillMaxHeight(), contentAlignment = Alignment.BottomCenter) {
            BottomMenuBar(navController, destinations)
        }
    }
}