package org.morfly.airin.sample.imagelist

import org.morfly.airin.sample.core.FeatureEntry


abstract class ImageListEntry : FeatureEntry {

    override val route = "imageList"

    override val args = emptyList<Nothing>()
}