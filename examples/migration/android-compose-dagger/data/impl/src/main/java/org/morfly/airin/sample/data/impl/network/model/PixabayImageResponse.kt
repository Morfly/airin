@file:Suppress("SpellCheckingInspection")

package org.morfly.airin.sample.data.impl.network.model

import com.squareup.moshi.Json


data class PixabayImageResponse(
    val total: Int,
    val totalHits: Int,
    val hits: List<PixabayImage>
)

data class PixabayImage(
    val id: Long,
    val type: String,
    val tags: String,
    @Json(name = "previewURL") val previewUrl: String,
    val previewWidth: Int,
    val previewHeight: Int,
    @Json(name = "webformatURL") val webformatUrl: String,
    val webformatWidth: Int,
    val webformatHeight: Int,
    @Json(name = "largeImageURL") val largeImageUrl: String,
    @Json(name = "fullHDURL") val fullHdUrl: String?,
    @Json(name = "imageURL") val imageUrl: String?,
    val imageWidth: Int,
    val imageHeight: Int,
    val imageSize: Long,
    val views: Int,
    val downloads: Int,
    val favorites: Int,
    val likes: Int,
    val comments: Int,
    @Json(name = "user_id") val userId: Long,
    val user: String,
    @Json(name = "userImageURL") val userImageUrl: String
)