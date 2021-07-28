package org.morfly.sample.feed

import androidx.annotation.StringRes
import org.morfly.sample.imagedata.Image
import org.morfly.sample.R


sealed class FeedViewState(open val images: List<Image>) {

    object Idle : FeedViewState(images = emptyList()) {

        @get:StringRes
        val noImagesTextRes: Int
            get() = R.string.no_images
    }

    data class Loading(override val images: List<Image>) : FeedViewState(images)

    data class Loaded(override val images: List<Image>) : FeedViewState(images)
}