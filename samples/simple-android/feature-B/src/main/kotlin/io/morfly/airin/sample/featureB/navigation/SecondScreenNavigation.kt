package io.morfly.airin.sample.featureB.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import io.morfly.airin.sample.featureB.ui.SecondScreen

const val secondScreenRoute = "second_screen_route"

fun NavController.navigateToSecondScreen(navOptions: NavOptions? = null) {
    navigate(secondScreenRoute, navOptions)
}

fun NavGraphBuilder.secondScreen(
    onNextClick: () -> Unit
) {
    composable(secondScreenRoute) {
        SecondScreen(onNextClick = onNextClick)
    }
}