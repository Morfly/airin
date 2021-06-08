@file:Suppress("LocalVariableName")

package org.morfly.airin.sample.ui

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


@Composable
fun Navigation() {
    val navController = rememberNavController()
    val destinations = LocalAppProvider.current.destinations
    val ImageListScreen = destinations.entry<ImageListEntry>()

    Box(Modifier.fillMaxSize()) {
        NavHost(navController, startDestination = ImageListScreen.route) {
            composable(ImageListScreen.route) {
                ImageListScreen(navController, destinations)
            }
        }
        Box(Modifier.fillMaxHeight(), contentAlignment = Alignment.BottomCenter) {
            BottomMenuBar(navController, destinations)
        }
    }
}