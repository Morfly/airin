package org.morfly.sample.imagedata


interface ImagesRepository {

    suspend fun loadImages(): List<Image>
}