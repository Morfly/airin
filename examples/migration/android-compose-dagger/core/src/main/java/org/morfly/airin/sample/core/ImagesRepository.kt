package org.morfly.airin.sample.core

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.morfly.airin.sample.core.entity.Image


/**
 * Repository for retrieving images.
 */
interface ImagesRepository {

    fun getPagedImages(query: String): Flow<PagingData<Image>>

    fun getUserImages(userId: Long): Flow<List<Image>>

    suspend fun getImage(id: String): Image?


    companion object {
        const val PAGE_SIZE = 10
    }
}