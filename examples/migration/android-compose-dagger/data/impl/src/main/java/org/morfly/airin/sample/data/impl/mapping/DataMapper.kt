package org.morfly.airin.sample.data.impl.mapping

import org.morfly.airin.sample.data.impl.network.model.PixabayImage
import org.morfly.airin.sample.data.impl.storage.entity.Image


interface DataMapper {

    /**
     * Maps image model from network api to the storage one.
     */
    fun networkToStorage(image: PixabayImage, query: String): Image
}