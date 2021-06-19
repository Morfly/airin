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

package org.morfly.airin.sample.data.impl.storage.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.morfly.airin.sample.data.impl.storage.entity.Image


@Dao
interface ImagesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(images: List<Image>)

    @Query("SELECT * FROM images WHERE `query` = :query ORDER BY timestamp ASC")
    fun getImages(query: String): PagingSource<Int, Image>

    @Query("SELECT * FROM images WHERE user_id = :userId ORDER BY timestamp ASC")
    fun getUserImages(userId: Long): Flow<List<Image>>

    @Query("SELECT * FROM images WHERE id = :id")
    suspend fun getImageById(id: String): Image?

    @Query("DELETE FROM images WHERE `query` = :query")
    suspend fun deleteByQuery(query: String)
}