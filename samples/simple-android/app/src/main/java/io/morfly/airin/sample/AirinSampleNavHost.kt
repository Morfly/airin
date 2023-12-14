package io.morfly.airin.sample

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import io.morfly.airin.sample.featureA.navigation.firstScreen
import io.morfly.airin.sample.featureA.navigation.firstScreenRoute
import io.morfly.airin.sample.featureA.navigation.navigateToFirstScreen
import io.morfly.airin.sample.featureB.navigation.navigateToSecondScreen
import io.morfly.airin.sample.featureB.navigation.secondScreen


@Composable
fun AirinSampleNavHost() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = firstScreenRoute) {
        firstScreen(onNextClick = navController::navigateToSecondScreen)
        secondScreen(onNextClick = navController::navigateToFirstScreen)
    }
}