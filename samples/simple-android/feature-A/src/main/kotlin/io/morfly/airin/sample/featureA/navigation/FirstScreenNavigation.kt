package io.morfly.airin.sample.featureA.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import io.morfly.airin.sample.featureA.ui.FirstScreen

const val firstScreenRoute = "first_screen_route"

fun NavController.navigateToFirstScreen(navOptions: NavOptions? = null) {
    navigate(firstScreenRoute, navOptions)
}

fun NavGraphBuilder.firstScreen(
    onNextClick: () -> Unit
) {
    composable(firstScreenRoute) {
        FirstScreen(onNextClick = onNextClick)
    }
}