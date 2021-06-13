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