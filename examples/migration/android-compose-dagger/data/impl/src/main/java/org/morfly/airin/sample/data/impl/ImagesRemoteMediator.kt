package org.morfly.airin.sample.data.impl

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.LoadType.*
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import org.morfly.airin.sample.core.ImagesRepository
import org.morfly.airin.sample.data.impl.mapping.DataMapper
import org.morfly.airin.sample.data.impl.network.PixabayApi
import org.morfly.airin.sample.data.impl.storage.AppDatabase
import org.morfly.airin.sample.data.impl.storage.entity.Image
import org.morfly.airin.sample.data.impl.storage.entity.ImageRemoteKeys
import retrofit2.HttpException
import java.io.IOException


const val FIRST_PAGE = 1

@OptIn(ExperimentalPagingApi::class)
class ImagesRemoteMediator(
    private val query: String,
    private val networkService: PixabayApi,
    private val database: AppDatabase,
    private val mapper: DataMapper
) : RemoteMediator<Int, Image>() {

    private val imagesDao = database.imagesDao()
    private val imagesRemoteKeysDao = database.imageRemoteKeysDao()

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Image>): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                REFRESH -> FIRST_PAGE
                PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                APPEND -> {
                    val lastItemRemoteKeys = findRemoteKeysForLastItem(state)

                    if (lastItemRemoteKeys?.nextKey == null) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }

                    lastItemRemoteKeys.nextKey
                }
            }

            val images = networkService.images(query, loadKey, ImagesRepository.PAGE_SIZE)
                .hits
                .map { mapper.networkToStorage(it, query) }

            println("imagesTTT: $images")

            val endOfPaginationReached = images.isEmpty()

            database.withTransaction {
                if (loadType == REFRESH) {
                    imagesDao.deleteByQuery(query)
                    imagesRemoteKeysDao.deleteAll()
                }
                val prevKey = if (loadKey == FIRST_PAGE) null else loadKey - 1
                val nextKey = if (endOfPaginationReached) null else loadKey + 1

                val remoteKeys = images.map { ImageRemoteKeys(it.id, prevKey, nextKey) }

                imagesDao.insertAll(images)
                imagesRemoteKeysDao.insertAll(remoteKeys)
            }
            MediatorResult.Success(endOfPaginationReached)
        } catch (exception: IOException) {
            MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            MediatorResult.Error(exception)
        }
    }

    private suspend fun findRemoteKeysForLastItem(state: PagingState<Int, Image>): ImageRemoteKeys? =
        state.pages
            .lastOrNull { it.data.isNotEmpty() }
            ?.data
            ?.lastOrNull()
            ?.let { image -> imagesRemoteKeysDao.imageRemoteKeys(image.id) }
}