package org.morfly.airin.sample.data.impl.storage.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.morfly.airin.sample.core.entity.Image as DomainImage
import org.morfly.airin.sample.core.entity.User as DomainUser


@Entity(tableName = "images")
data class Image(
    @PrimaryKey override val id: Long,
    override val query: String,
    override val url: String,
    @ColumnInfo(name = "preview_url") override val previewUrl: String,
    override val tags: String,
    override val views: Int,
    override val downloads: Int,
    override val favorites: Int,
    override val likes: Int,
    override val comments: Int,
    override val timestamp: Long,
    @Embedded(prefix = "user_") override val user: User
) : DomainImage

data class User(
    @ColumnInfo(name = "id") override val id: Long,
    override val name: String,
    @ColumnInfo(name = "image_url") override val imageUrl: String
) : DomainUser