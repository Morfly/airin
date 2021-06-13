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