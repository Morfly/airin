/*
 * Copyright 2021 Pavlo Stavytskyi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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