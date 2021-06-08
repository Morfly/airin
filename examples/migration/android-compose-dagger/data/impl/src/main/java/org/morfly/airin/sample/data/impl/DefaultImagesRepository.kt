package org.morfly.airin.sample.data.impl

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import org.morfly.airin.sample.core.ImagesRepository
import org.morfly.airin.sample.core.entity.Image
import org.morfly.airin.sample.data.impl.mapping.DataMapper
import org.morfly.airin.sample.data.impl.network.PixabayApi
import org.morfly.airin.sample.data.impl.storage.AppDatabase
import javax.inject.Inject


class DefaultImagesRepository @Inject constructor(
    private val networkService: PixabayApi,
    private val database: AppDatabase,
    private val mapper: DataMapper,
    private val backgroundDispatcher: CoroutineDispatcher
) : ImagesRepository {

    @Suppress("UNCHECKED_CAST")
    @OptIn(ExperimentalPagingApi::class)
    override fun getPagedImages(query: String): Flow<PagingData<Image>> =
        Pager(
            config = PagingConfig(
                pageSize = ImagesRepository.PAGE_SIZE,
                enablePlaceholders = true,
//                initialLoadSize = ImagesRepository.PAGE_SIZE
            ),
            remoteMediator = ImagesRemoteMediator(query, networkService, database, mapper),
            pagingSourceFactory = { database.imagesDao().getImages(query) }
        ).flow.flowOn(backgroundDispatcher).also { println("getPagedImages") } as Flow<PagingData<Image>>

    override fun getUserImages(userId: Long): Flow<List<Image>> =
        database.imagesDao().getUserImages(userId)

    override suspend fun getImage(id: String): Image? =
        database.imagesDao().getImageById(id)
}