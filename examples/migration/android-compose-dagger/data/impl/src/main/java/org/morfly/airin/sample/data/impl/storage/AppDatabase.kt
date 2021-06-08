package org.morfly.airin.sample.data.impl.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.morfly.airin.sample.data.impl.storage.dao.ImageRemoteKeysDao
import org.morfly.airin.sample.data.impl.storage.dao.ImagesDao
import org.morfly.airin.sample.data.impl.storage.entity.Image
import org.morfly.airin.sample.data.impl.storage.entity.ImageRemoteKeys


@Database(entities = [Image::class, ImageRemoteKeys::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun imagesDao(): ImagesDao

    abstract fun imageRemoteKeysDao(): ImageRemoteKeysDao

    companion object {
        private const val DATABASE_NAME = "app_db"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context)
                    .also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context): AppDatabase =
            Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }
}