package org.morfly.sample.details

import android.os.Bundle
import androidx.core.os.bundleOf
import org.morfly.sample.Route
import org.morfly.sample.details.DetailsFragment.Companion.ARG_IMAGE_ID
import org.morfly.sample.details.DetailsFragment.Companion.ARG_IMAGE_URL
import org.morfly.sample.imagedata.Image


data class DetailsRoute(val image: Image) : Route {

    override val args: Bundle
        get() = bundleOf(
            ARG_IMAGE_ID to image.id,
            ARG_IMAGE_URL to image.url
        )
}