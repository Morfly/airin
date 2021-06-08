package org.morfly.airin.sample.data.impl.mapping

import org.morfly.airin.sample.data.impl.network.model.PixabayImage
import org.morfly.airin.sample.data.impl.storage.entity.Image
import org.morfly.airin.sample.data.impl.storage.entity.User
import javax.inject.Inject


class DefaultDataMapper @Inject constructor() : DataMapper {

    override fun networkToStorage(image: PixabayImage, query: String) = with(image) {
        Image(
            id = id,
            query = query,
            url = largeImageUrl,
            previewUrl = previewUrl,
            tags = tags,
            views = views,
            downloads = downloads,
            favorites = favorites,
            likes = likes,
            comments = comments,
            timestamp = System.nanoTime(),
            user = User(
                id = userId,
                name = user,
                imageUrl = userImageUrl
            )
        )
    }
}