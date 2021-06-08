@file:SuppressLint("ComposableNaming")

package org.morfly.airin.sample.imagelist.impl

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import org.morfly.airin.sample.core.FeatureEntries
import org.morfly.airin.sample.core.di.lib.injectedViewModel
import org.morfly.airin.sample.data.LocalDataProvider
import org.morfly.airin.sample.imagelist.ImageListEntry
import org.morfly.airin.sample.imagelist.impl.di.DaggerImageListComponent
import org.morfly.airin.sample.imagelist.impl.ui.ImageListScreen
import javax.inject.Inject


class ImageListEntryImpl @Inject constructor() : ImageListEntry() {

    override val route = "imageList"

    @Composable
    override fun invoke(navController: NavController, destinations: FeatureEntries) {
        val viewModel = injectedViewModel {
            DaggerImageListComponent.builder()
                .dataProvider(LocalDataProvider.current)
                .build()
                .viewModel
        }

        ImageListScreen(viewModel)
    }
}