package com.mkirdev.unsplash.data.storages.database.factory

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mkirdev.unsplash.data.storages.database.dao.base.CollectionDao
import com.mkirdev.unsplash.data.storages.database.dao.base.PhotoCollectionDao
import com.mkirdev.unsplash.data.storages.database.dao.base.PhotoDao
import com.mkirdev.unsplash.data.storages.database.dao.base.PhotoReactionsDao
import com.mkirdev.unsplash.data.storages.database.dao.base.ReactionsTypeDao
import com.mkirdev.unsplash.data.storages.database.dao.base.RemoteKeysDao
import com.mkirdev.unsplash.data.storages.database.dao.base.UserDao
import com.mkirdev.unsplash.data.storages.database.dao.collection.PhotoFromCollectionDao
import com.mkirdev.unsplash.data.storages.database.dao.feed.PhotoFeedDao
import com.mkirdev.unsplash.data.storages.database.dao.search.PhotoSearchDao
import com.mkirdev.unsplash.data.storages.database.entities.CollectionEntity
import com.mkirdev.unsplash.data.storages.database.entities.PhotoCollectionEntity
import com.mkirdev.unsplash.data.storages.database.entities.PhotoEntity
import com.mkirdev.unsplash.data.storages.database.entities.PhotoReactionsEntity
import com.mkirdev.unsplash.data.storages.database.entities.ReactionsTypeEntity
import com.mkirdev.unsplash.data.storages.database.entities.RemoteKeysEntity
import com.mkirdev.unsplash.data.storages.database.entities.UserEntity

private const val DATABASE_VERSION = 1

@Database(
    entities = [
        CollectionEntity::class,
        PhotoCollectionEntity::class,
        PhotoEntity::class,
        PhotoReactionsEntity::class,
        ReactionsTypeEntity::class,
        UserEntity::class,
        RemoteKeysEntity::class
    ],
    version = DATABASE_VERSION
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun collectionDao(): CollectionDao

    abstract fun photoCollectionDao(): PhotoCollectionDao

    abstract fun photoDao(): PhotoDao

    abstract fun photoReactionsDao(): PhotoReactionsDao

    abstract fun reactionsTypeDao(): ReactionsTypeDao

    abstract fun userDao(): UserDao

    abstract fun photoFromCollectionDao(): PhotoFromCollectionDao

    abstract fun photoFeedDao(): PhotoFeedDao

    abstract fun photoSearchDao(): PhotoSearchDao

    abstract fun remoteKeysDao(): RemoteKeysDao
}