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

package org.morfly.airin.sample.core

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NamedNavArgument


typealias FeatureEntries = Map<Class<out FeatureEntry>, FeatureEntry>


interface FeatureEntry {

    val route: String

    val args: List<NamedNavArgument>

    @Composable
    operator fun invoke(navController: NavController, destinations: FeatureEntries, args: Bundle?)
}


inline fun <reified T : FeatureEntry> FeatureEntries.entry(): T =
    this[T::class.java] as? T ?: error("Unable to find ${T::class.java} destination.")

inline fun <reified T : FeatureEntry> FeatureEntries.route(): String =
    this.entry<T>().route