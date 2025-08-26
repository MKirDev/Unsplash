package com.mkirdev.unsplash.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mkirdev.unsplash.data.models.createReactionsFeedType
import com.mkirdev.unsplash.data.models.createPhotoFeed
import com.mkirdev.unsplash.data.models.createUserFeed
import com.mkirdev.unsplash.data.models.createPhotosForFeed
import com.mkirdev.unsplash.data.models.createRemoteKeysFeed
import com.mkirdev.unsplash.data.models.createPhotoReactionsFeed
import com.mkirdev.unsplash.data.models.createUsersForFeed
import com.mkirdev.unsplash.data.storages.database.dao.feed.PhotoFeedDao
import com.mkirdev.unsplash.data.storages.database.dao.feed.PhotoReactionsFeedDao
import com.mkirdev.unsplash.data.storages.database.dao.feed.ReactionsFeedDao
import com.mkirdev.unsplash.data.storages.database.dao.feed.RemoteKeysFeedDao
import com.mkirdev.unsplash.data.storages.database.dao.feed.UserFeedDao
import com.mkirdev.unsplash.data.storages.database.entities.feed.PhotoReactionsFeedEntity
import com.mkirdev.unsplash.data.storages.database.entities.feed.ReactionsFeedEntity
import com.mkirdev.unsplash.data.storages.database.entities.feed.UserFeedEntity
import com.mkirdev.unsplash.data.storages.database.factory.AppDatabase
import kotlinx.coroutines.test.runTest
import org.junit.AfterClass
import org.junit.Assert
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {

    companion object {
        private lateinit var db: AppDatabase

        private val context = ApplicationProvider.getApplicationContext<Context>()

        private lateinit var photoFeedDao: PhotoFeedDao
        private lateinit var reactionsFeedDao: ReactionsFeedDao
        private lateinit var photoReactionsFeedDao: PhotoReactionsFeedDao
        private lateinit var remoteKeysFeedDao: RemoteKeysFeedDao
        private lateinit var userFeedDao: UserFeedDao

        @BeforeClass
        @JvmStatic
        fun setup() {
            db = Room.databaseBuilder(context, AppDatabase::class.java, "test_db.db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()

            photoFeedDao = db.photoFeedDao()
            reactionsFeedDao = db.reactionsFeedDao()
            photoReactionsFeedDao = db.photoReactionsFeedDao()
            remoteKeysFeedDao = db.remoteKeysFeedDao()
            userFeedDao = db.userFeedDao()


        }

        @AfterClass
        @JvmStatic
        fun tearDown() {
            db.close()
            context.deleteDatabase("test_db.db")
        }
    }

    @Test
    fun givenPhotoFeedIsAdded_whenFetched_thenItMatchesExpectedData() = runTest {
        val userId = "user1"
        val photoId = "photo1"
        val user = createUserFeed(userId)
        val photo = createPhotoFeed(photoId, userId)

        userFeedDao.addUser(user)
        photoFeedDao.addPhoto(photo)

        val fetched = photoFeedDao.getPhoto(photoId)

        Assert.assertNotNull(fetched)
        Assert.assertEquals(photoId, fetched?.photoId)
    }

    @Test
    fun givenUserFeedIsAdded_whenDeleted_thenUserIsNoLongerInDatabase() = runTest {
        val userId = "id"
        val user = createUserFeed(userId)

        userFeedDao.addUser(user)

        val result = db.query("SELECT * FROM ${UserFeedEntity.TABLE_NAME}", null)
        Assert.assertTrue(result.count > 0)

        userFeedDao.deleteUser(userId)
        val afterDelete =
            db.query("SELECT * FROM ${UserFeedEntity.TABLE_NAME} WHERE user_id = '$userId'", null)
        Assert.assertTrue(afterDelete.count == 0)
    }

    @Test
    fun givenReactionFeedExist_whenDeleted_thenReactionIsNoLongerInDatabase() {
        val photoId = "id"
        val reactionFeed = createReactionsFeedType(photoId)

        reactionsFeedDao.addReactionType(reactionFeed)

        val result = db.query("SELECT * FROM ${ReactionsFeedEntity.TABLE_NAME}", null)
        Assert.assertTrue(result.count > 0)

        reactionsFeedDao.deleteReactionsType(photoId)
        val afterDelete =
            db.query("SELECT * FROM ${ReactionsFeedEntity.TABLE_NAME} WHERE photo_id = '$photoId'", null)
        Assert.assertTrue(afterDelete.count == 0)
    }


    @Test
    fun givenPhotoReactionsExist_whenDeleted_thenTableIsEmpty() {
        val id = "id"
        val user = createUserFeed(id)
        val photo = createPhotoFeed(id, id)
        val reactionsType = createReactionsFeedType(id)
        val photoReactions = createPhotoReactionsFeed(id, id)

        userFeedDao.addUser(user)
        photoFeedDao.addPhoto(photo)
        reactionsFeedDao.addReactionType(reactionsType)


        photoReactionsFeedDao.addPhotoReaction(photoReactions)

        val result = db.query("SELECT * FROM ${PhotoReactionsFeedEntity.TABLE_NAME}", null)
        Assert.assertTrue(result.count > 0)

        photoReactionsFeedDao.deletePhotoReactions()
        val afterDelete = db.query("SELECT * FROM ${PhotoReactionsFeedEntity.TABLE_NAME}", null)
        Assert.assertTrue(afterDelete.count == 0)
    }

    @Test
    fun givenRemoteKeysFeedIsAdded_whenFetched_thenItMatchesExpectedData() = runTest {
        val users = createUsersForFeed()
        val photos = createPhotosForFeed()
        val keys = createRemoteKeysFeed()

        userFeedDao.addUsers(users)
        photoFeedDao.addPhotos(photos)
        remoteKeysFeedDao.addAllRemoteKeys(keys)

        val fetched = remoteKeysFeedDao.getRemoteKeys(photos.first().photoId)

        Assert.assertNotNull(fetched)
        Assert.assertEquals(photos.first().photoId, fetched.photoId)
    }

}