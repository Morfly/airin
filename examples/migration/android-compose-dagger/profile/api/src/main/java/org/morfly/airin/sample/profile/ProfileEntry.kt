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