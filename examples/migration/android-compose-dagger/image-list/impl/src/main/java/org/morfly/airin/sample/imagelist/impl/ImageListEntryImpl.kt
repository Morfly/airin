@file:SuppressLint("ComposableNaming")

package org.morfly.airin.sample.imagelist.impl

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import org.morfly.airin.sample.core.FeatureEntries
import org.morfly.airin.sample.core.di.lib.injectedViewModel
import org.morfly.airin.sample.core.entry
import org.morfly.airin.sample.data.LocalDataProvider
import org.morfly.airin.sample.imagelist.ImageListEntry
import org.morfly.airin.sample.imagelist.impl.di.DaggerImageListComponent
import org.morfly.airin.sample.imagelist.impl.ui.ImageListScreen
import org.morfly.airin.sample.profile.ProfileEntry
import javax.inject.Inject


class ImageListEntryImpl @Inject constructor() : ImageListEntry() {

    @Composable
    override fun invoke(navController: NavController, destinations: FeatureEntries, args: Bundle?) {
        val viewModel = injectedViewModel {
            DaggerImageListComponent.builder()
                .dataProvider(LocalDataProvider.current)
                .build()
                .viewModel
        }

        ImageListScreen(viewModel, onUserSelected = { userId ->
            val profile = destinations.entry<ProfileEntry>().route(userId)
            navController.navigate(profile)
        })
    }
}