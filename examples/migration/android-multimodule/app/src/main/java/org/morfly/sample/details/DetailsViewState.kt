package org.morfly.sample.details

import org.morfly.sample.imagedata.Image as DomainImage


sealed class DetailsViewState {

    data class Image(val id: Long, val url: String) : DetailsViewState() {

        constructor(image: DomainImage) : this(image.id, image.url)
    }
}