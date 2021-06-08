package org.morfly.airin.sample.data.impl.storage.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "image_remote_keys")
data class ImageRemoteKeys(
    @PrimaryKey @ColumnInfo(name = "image_id") val imageId: Long,
    @ColumnInfo(name = "prev_key") val prevKey: Int?,
    @ColumnInfo(name = "next_key") val nextKey: Int?
)