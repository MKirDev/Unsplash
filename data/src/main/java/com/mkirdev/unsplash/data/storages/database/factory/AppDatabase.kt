package com.mkirdev.unsplash.data.storages.database.factory

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mkirdev.unsplash.data.storages.database.dao.collection.RemoteKeysCollectionDao
import com.mkirdev.unsplash.data.storages.database.dao.feed.RemoteKeysFeedDao
import com.mkirdev.unsplash.data.storages.database.dao.search.RemoteKeysSearchDao
import com.mkirdev.unsplash.data.storages.database.dao.collection.PhotoCollectionDao
import com.mkirdev.unsplash.data.storages.database.dao.collection.PhotoCollectionJoinedDao
import com.mkirdev.unsplash.data.storages.database.dao.collection.PhotoReactionsCollectionDao
import com.mkirdev.unsplash.data.storages.database.dao.collection.ReactionsCollectionDao
import com.mkirdev.unsplash.data.storages.database.dao.collection.UserCollectionDao
import com.mkirdev.unsplash.data.storages.database.dao.feed.PhotoFeedDao
import com.mkirdev.unsplash.data.storages.database.dao.feed.PhotoFeedJoinedDao
import com.mkirdev.unsplash.data.storages.database.dao.feed.PhotoReactionsFeedDao
import com.mkirdev.unsplash.data.storages.database.dao.feed.ReactionsFeedDao
import com.mkirdev.unsplash.data.storages.database.dao.feed.UserFeedDao
import com.mkirdev.unsplash.data.storages.database.dao.liked.PhotoLikedDao
import com.mkirdev.unsplash.data.storages.database.dao.liked.PhotoLikedJoinedDao
import com.mkirdev.unsplash.data.storages.database.dao.liked.PhotoReactionsLikedDao
import com.mkirdev.unsplash.data.storages.database.dao.liked.ReactionsLikedDao
import com.mkirdev.unsplash.data.storages.database.dao.liked.RemoteKeysLikedDao
import com.mkirdev.unsplash.data.storages.database.dao.liked.UserLikedDao
import com.mkirdev.unsplash.data.storages.database.dao.search.PhotoReactionsSearchDao
import com.mkirdev.unsplash.data.storages.database.dao.search.PhotoSearchDao
import com.mkirdev.unsplash.data.storages.database.dao.search.PhotoSearchJoinedDao
import com.mkirdev.unsplash.data.storages.database.dao.search.ReactionsSearchDao
import com.mkirdev.unsplash.data.storages.database.dao.search.UserSearchDao
import com.mkirdev.unsplash.data.storages.database.dao.user.UserProfileDao
import com.mkirdev.unsplash.data.storages.database.entities.collection.RemoteKeysCollectionEntity
import com.mkirdev.unsplash.data.storages.database.entities.feed.RemoteKeysFeedEntity
import com.mkirdev.unsplash.data.storages.database.entities.search.RemoteKeysSearchEntity
import com.mkirdev.unsplash.data.storages.database.entities.collection.PhotoCollectionEntity
import com.mkirdev.unsplash.data.storages.database.entities.collection.PhotoReactionsCollectionEntity
import com.mkirdev.unsplash.data.storages.database.entities.collection.ReactionsCollectionEntity
import com.mkirdev.unsplash.data.storages.database.entities.collection.UserCollectionEntity
import com.mkirdev.unsplash.data.storages.database.entities.feed.PhotoFeedEntity
import com.mkirdev.unsplash.data.storages.database.entities.feed.PhotoReactionsFeedEntity
import com.mkirdev.unsplash.data.storages.database.entities.feed.ReactionsFeedEntity
import com.mkirdev.unsplash.data.storages.database.entities.feed.UserFeedEntity
import com.mkirdev.unsplash.data.storages.database.entities.liked.PhotoLikedEntity
import com.mkirdev.unsplash.data.storages.database.entities.liked.PhotoReactionsLikedEntity
import com.mkirdev.unsplash.data.storages.database.entities.liked.ReactionsLikedEntity
import com.mkirdev.unsplash.data.storages.database.entities.liked.RemoteKeysLikedEntity
import com.mkirdev.unsplash.data.storages.database.entities.liked.UserLikedEntity
import com.mkirdev.unsplash.data.storages.database.entities.search.PhotoReactionsSearchEntity
import com.mkirdev.unsplash.data.storages.database.entities.search.PhotoSearchEntity
import com.mkirdev.unsplash.data.storages.database.entities.search.ReactionsSearchEntity
import com.mkirdev.unsplash.data.storages.database.entities.search.UserSearchEntity
import com.mkirdev.unsplash.data.storages.database.entities.user.UserProfileEntity
import com.mkirdev.unsplash.data.storages.database.view.PhotoLikedJoinedView

private const val DATABASE_VERSION = 2

@Database(
    entities = [
        PhotoFeedEntity::class,
        UserFeedEntity::class,
        ReactionsFeedEntity::class,
        PhotoReactionsFeedEntity::class,
        RemoteKeysFeedEntity::class,
        PhotoSearchEntity::class,
        UserSearchEntity::class,
        ReactionsSearchEntity::class,
        PhotoReactionsSearchEntity::class,
        RemoteKeysSearchEntity::class,
        PhotoCollectionEntity::class,
        UserCollectionEntity::class,
        ReactionsCollectionEntity::class,
        PhotoReactionsCollectionEntity::class,
        RemoteKeysCollectionEntity::class,
        PhotoLikedEntity::class,
        UserLikedEntity::class,
        ReactionsLikedEntity::class,
        PhotoReactionsLikedEntity::class,
        RemoteKeysLikedEntity::class,
        UserProfileEntity::class
    ],
    views = [PhotoLikedJoinedView::class],
    version = DATABASE_VERSION
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun photoFeedDao(): PhotoFeedDao

    abstract fun photoFeedJoinedDao(): PhotoFeedJoinedDao

    abstract fun photoReactionsFeedDao(): PhotoReactionsFeedDao

    abstract fun reactionsFeedDao(): ReactionsFeedDao

    abstract fun userFeedDao(): UserFeedDao

    abstract fun remoteKeysFeedDao(): RemoteKeysFeedDao


    abstract fun photoSearchDao(): PhotoSearchDao

    abstract fun photoSearchJoinedDao(): PhotoSearchJoinedDao

    abstract fun photoReactionsSearchDao(): PhotoReactionsSearchDao

    abstract fun reactionsSearchDao(): ReactionsSearchDao

    abstract fun userSearchDao(): UserSearchDao

    abstract fun remoteKeysSearchDao(): RemoteKeysSearchDao


    abstract fun photoCollectionDao(): PhotoCollectionDao

    abstract fun photoCollectionJoinedDao(): PhotoCollectionJoinedDao

    abstract fun photoReactionsCollectionDao(): PhotoReactionsCollectionDao

    abstract fun reactionsCollectionDao(): ReactionsCollectionDao

    abstract fun userCollectionDao(): UserCollectionDao

    abstract fun remoteKeysCollectionDao(): RemoteKeysCollectionDao


    abstract fun photoLikedDao(): PhotoLikedDao

    abstract fun photoLikedJoinedDao(): PhotoLikedJoinedDao

    abstract fun photoReactionsLikedDao(): PhotoReactionsLikedDao

    abstract fun reactionsLikedDao(): ReactionsLikedDao

    abstract fun userLikedDao(): UserLikedDao

    abstract fun remoteKeysLikedDao(): RemoteKeysLikedDao

    abstract fun userProfileDao(): UserProfileDao

}