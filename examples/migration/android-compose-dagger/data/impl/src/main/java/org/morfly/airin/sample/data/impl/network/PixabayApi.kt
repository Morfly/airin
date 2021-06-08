@file:Suppress("unused")

package org.morfly.airin.sample.data.impl.network

import org.morfly.airin.sample.data.impl.network.model.PixabayImageResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface PixabayApi {

    companion object {
        const val BASE_URL = "https://pixabay.com/api/"
    }

    @GET(".")
    suspend fun images(
        @Query("q") q: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("lang") lang: String? = null,
        @Query("image_type") imageType: String = "photo",
        @Query("orientation") orientation: String = "all",
        @Query("category") category: String? = null,
        @Query("min_width") minWidth: Int? = null,
        @Query("min_height") minHeight: Int? = null,
        @Query("colors") colors: String? = null,
        @Query("editors_choice") editorsChoice: Boolean = true,
        @Query("safesearch") safeSearch: Boolean = true,
        @Query("order") order: String = "popular"
    ): PixabayImageResponse

    @GET(".")
    suspend fun image(@Query("id") id: String): PixabayImageResponse
}