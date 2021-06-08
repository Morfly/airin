package org.morfly.airin.sample.imagelist.impl.di

import dagger.Component
import org.morfly.airin.sample.core.di.lib.ScreenScoped
import org.morfly.airin.sample.data.DataProvider
import org.morfly.airin.sample.imagelist.ImageListProvider
import org.morfly.airin.sample.imagelist.impl.ImageListViewModel


@ScreenScoped
@Component(dependencies = [DataProvider::class])
interface ImageListComponent : ImageListProvider {

    val viewModel: ImageListViewModel
}