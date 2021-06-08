@file:SuppressLint("ComposableNaming")

package org.morfly.airin.sample.core

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.NavController


typealias FeatureEntries = Map<Class<out FeatureEntry>, FeatureEntry>


interface FeatureEntry {

    val route: String

    @Composable
    operator fun invoke(navController: NavController, destinations: FeatureEntries)
}


inline fun <reified T : FeatureEntry> FeatureEntries.entry(): FeatureEntry =
    this[T::class.java] ?: error("Unable to find ${T::class.java} destination.")

inline fun <reified T : FeatureEntry> FeatureEntries.route(): String =
    this.entry<T>().route