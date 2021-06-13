package org.morfly.airin.sample.profile

import androidx.navigation.NavType
import androidx.navigation.compose.navArgument
import org.morfly.airin.sample.core.FeatureEntry


abstract class ProfileEntry : FeatureEntry {

    override val route = "profile/{$USER_ID_ARG}"

    override val args = listOf(
        navArgument(USER_ID_ARG) { type = NavType.LongType }
    )

    fun route(userId: Long): String =
        "profile/$userId"


    companion object {
        const val USER_ID_ARG = "userId"
    }
}