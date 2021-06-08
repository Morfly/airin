package org.morfly.airin.sample.data.impl.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.morfly.airin.sample.data.impl.storage.entity.ImageRemoteKeys


@Dao
interface ImageRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKeys: List<ImageRemoteKeys>)

    @Query("SELECT * FROM image_remote_keys WHERE image_id = :imageId")
    suspend fun imageRemoteKeys(imageId: Long): ImageRemoteKeys?

    @Query("DELETE FROM image_remote_keys")
    suspend fun deleteAll()
}