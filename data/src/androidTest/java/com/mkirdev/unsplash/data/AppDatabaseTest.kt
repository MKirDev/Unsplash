package com.mkirdev.unsplash.data

import android.content.Context
import androidx.paging.PagingSource
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mkirdev.unsplash.data.models.createSingleReactionsType
import com.mkirdev.unsplash.data.models.createSinglePhoto
import com.mkirdev.unsplash.data.models.createSingleUser
import com.mkirdev.unsplash.data.models.createCollections
import com.mkirdev.unsplash.data.models.createPhotoCollections
import com.mkirdev.unsplash.data.models.createPhotoReactionsForCollections
import com.mkirdev.unsplash.data.models.createPhotoReactionsForFeed
import com.mkirdev.unsplash.data.models.createPhotoReactionsForSearch
import com.mkirdev.unsplash.data.models.createPhotosForCollections
import com.mkirdev.unsplash.data.models.createPhotosForFeed
import com.mkirdev.unsplash.data.models.createPhotosForSearch
import com.mkirdev.unsplash.data.models.createReactionsTypeForCollections
import com.mkirdev.unsplash.data.models.createReactionsTypeForFeed
import com.mkirdev.unsplash.data.models.createReactionsTypeForSearch
import com.mkirdev.unsplash.data.models.createRemoteKeysFeed
import com.mkirdev.unsplash.data.models.createRemoteKeysSearch
import com.mkirdev.unsplash.data.models.createSinglePhotoReactions
import com.mkirdev.unsplash.data.models.createUsersForCollections
import com.mkirdev.unsplash.data.models.createUsersForFeed
import com.mkirdev.unsplash.data.models.createUsersForSearch
import com.mkirdev.unsplash.data.storages.database.dao.base.CollectionDao
import com.mkirdev.unsplash.data.storages.database.dao.base.PhotoCollectionDao
import com.mkirdev.unsplash.data.storages.database.dao.base.PhotoDao
import com.mkirdev.unsplash.data.storages.database.dao.base.PhotoReactionsDao
import com.mkirdev.unsplash.data.storages.database.dao.base.ReactionsTypeDao
import com.mkirdev.unsplash.data.storages.database.dao.base.RemoteKeysFeedDao
import com.mkirdev.unsplash.data.storages.database.dao.base.RemoteKeysSearchDao
import com.mkirdev.unsplash.data.storages.database.dao.base.UserDao
import com.mkirdev.unsplash.data.storages.database.dao.collection.PhotoFromCollectionDao
import com.mkirdev.unsplash.data.storages.database.dao.feed.PhotoFeedDao
import com.mkirdev.unsplash.data.storages.database.dao.search.PhotoSearchDao
import com.mkirdev.unsplash.data.storages.database.entities.CollectionEntity
import com.mkirdev.unsplash.data.storages.database.entities.PhotoCollectionEntity
import com.mkirdev.unsplash.data.storages.database.entities.PhotoReactionsEntity
import com.mkirdev.unsplash.data.storages.database.entities.ReactionsTypeEntity
import com.mkirdev.unsplash.data.storages.database.entities.UserEntity
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

        private lateinit var collectionDao: CollectionDao
        private lateinit var photoDao: PhotoDao

        private lateinit var reactionsTypeDao: ReactionsTypeDao
        private lateinit var photoCollectionDao: PhotoCollectionDao
        private lateinit var photoReactionsDao: PhotoReactionsDao
        private lateinit var remoteKeysFeedDao: RemoteKeysFeedDao
        private lateinit var remoteKeysSearchDao: RemoteKeysSearchDao
        private lateinit var userDao: UserDao
        private lateinit var photoFromCollectionDao: PhotoFromCollectionDao
        private lateinit var photoFeedDao: PhotoFeedDao
        private lateinit var photoSearchDao: PhotoSearchDao

        @BeforeClass
        @JvmStatic
        fun setup() {
            db = Room.databaseBuilder(context, AppDatabase::class.java, "test_db.db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()

            collectionDao = db.collectionDao()
            photoCollectionDao = db.photoCollectionDao()
            photoDao = db.photoDao()
            reactionsTypeDao = db.reactionsTypeDao()
            photoReactionsDao = db.photoReactionsDao()
            remoteKeysFeedDao = db.remoteKeysFeedDao()
            remoteKeysSearchDao = db.remoteKeysSearchDao()
            userDao = db.userDao()
            photoFromCollectionDao = db.photoFromCollectionDao()
            photoFeedDao = db.photoFeedDao()
            photoSearchDao = db.photoSearchDao()
        }

        @AfterClass
        @JvmStatic
        fun tearDown() {
            db.close()
            context.deleteDatabase("test_db.db")
        }
    }

    @Test
    fun givenCollectionsExist_whenDeleted_thenDatabaseIsEmpty() = runTest {
        val collections = createCollections()
        collectionDao.addCollections(collections)

        val result = db.query("SELECT * FROM ${CollectionEntity.TABLE_NAME}", null)
        Assert.assertTrue(result.count > 0)

        collectionDao.deleteCollections()
        val afterDelete = db.query("SELECT * FROM ${CollectionEntity.TABLE_NAME}", null)
        Assert.assertTrue(afterDelete.count == 0)
    }

    @Test
    fun givenPhotoIsAdded_whenFetched_thenItMatchesExpectedData() = runTest {
        val userId = "user1"
        val photoId = "photo1"
        val user = createSingleUser(userId)
        val photo = createSinglePhoto(photoId, userId)

        userDao.addUser(user)
        photoDao.addPhoto(photo)

        val fetched = photoDao.getPhoto(photoId)

        Assert.assertNotNull(fetched)
        Assert.assertEquals(photoId, fetched?.id)
    }

    @Test
    fun givenUserIsAdded_whenDeleted_thenUserIsNoLongerInDatabase() = runTest {
        val id = "id"
        val user = createSingleUser(id)

        userDao.addUser(user)

        val result = db.query("SELECT * FROM ${UserEntity.TABLE_NAME}", null)
        Assert.assertTrue(result.count > 0)

        userDao.deleteUser(id)
        val afterDelete = db.query("SELECT * FROM ${UserEntity.TABLE_NAME} WHERE user_id = '$id'", null)
        Assert.assertTrue(afterDelete.count == 0)
    }

    @Test
    fun givenPhotosCollectionsExist_whenDeleted_thenTableIsEmpty() {

        val users = createUsersForCollections()
        val photos = createPhotosForCollections()
        val collections = createCollections()

        userDao.addUsers(users)
        photoDao.addPhotos(photos)
        collectionDao.addCollections(collections)

        val photosCollections = createPhotoCollections()

        photoCollectionDao.addPhotosCollections(photosCollections)

        val result = db.query("SELECT * FROM ${PhotoCollectionEntity.TABLE_NAME}", null)
        Assert.assertTrue(result.count > 0)

        photoCollectionDao.deletePhotoCollection()
        val afterDelete = db.query("SELECT * FROM ${PhotoCollectionEntity.TABLE_NAME}", null)
        Assert.assertTrue(afterDelete.count == 0)
    }

    @Test
    fun givenReactionTypeExist_whenDeleted_thenReactionIsNoLongerInDatabase() {
        val id = "id"
        val reactionType = createSingleReactionsType(id)

        reactionsTypeDao.addReactionType(reactionType)

        val result = db.query("SELECT * FROM ${ReactionsTypeEntity.TABLE_NAME}", null)
        Assert.assertTrue(result.count > 0)

        reactionsTypeDao.deleteReactionsType(id)
        val afterDelete = db.query("SELECT * FROM ${ReactionsTypeEntity.TABLE_NAME} WHERE id = '$id'", null)
        Assert.assertTrue(afterDelete.count == 0)
    }


    @Test
    fun givenPhotoReactionsExist_whenDeleted_thenTableIsEmpty() {
        val id = "id"
        val user = createSingleUser(id)
        val photo = createSinglePhoto(id, id)
        val reactionsType = createSingleReactionsType(id)
        val photoReactions = createSinglePhotoReactions(id, id)

        userDao.addUser(user)
        photoDao.addPhoto(photo)
        reactionsTypeDao.addReactionType(reactionsType)


        photoReactionsDao.addPhotoReaction(photoReactions)

        val result = db.query("SELECT * FROM ${PhotoReactionsEntity.TABLE_NAME}", null)
        Assert.assertTrue(result.count > 0)

        photoReactionsDao.deletePhotoReactions()
        val afterDelete = db.query("SELECT * FROM ${PhotoReactionsEntity.TABLE_NAME}", null)
        Assert.assertTrue(afterDelete.count == 0)
    }

    @Test
    fun givenRemoteKeysFeedIsAdded_whenFetched_thenItMatchesExpectedData() = runTest {
        val users = createUsersForFeed()
        val photos = createPhotosForFeed()
        val keys = createRemoteKeysFeed()

        userDao.addUsers(users)
        photoDao.addPhotos(photos)
        remoteKeysFeedDao.addAllRemoteKeys(keys)

        val fetched = remoteKeysFeedDao.getRemoteKeys(photos.first().id)

        Assert.assertNotNull(fetched)
        Assert.assertEquals(photos.first().id, fetched.id)
    }


    @Test
    fun givenRemoteKeysSearchIsAdded_whenFetched_thenItMatchesExpectedData() = runTest {
        val users = createUsersForFeed()
        val photos = createPhotosForFeed()
        val keys = createRemoteKeysSearch()

        userDao.addUsers(users)
        photoDao.addPhotos(photos)
        remoteKeysSearchDao.addAllRemoteKeys(keys)

        val fetched = remoteKeysSearchDao.getRemoteKeys(photos.first().id)

        Assert.assertNotNull(fetched)
        Assert.assertEquals(photos.first().id, fetched.id)
    }


    @Test
    fun givenPhotoFromCollectionExist_whenDeleted_thenTableIsEmpty() = runTest {
        val users = createUsersForCollections()
        val photos = createPhotosForCollections()
        val reactionsType = createReactionsTypeForCollections()
        val collections = createCollections()
        val photoReactions = createPhotoReactionsForCollections()
        val photosCollections = createPhotoCollections()

        userDao.addUsers(users)
        reactionsTypeDao.addReactionsTypes(reactionsType)
        photoDao.addPhotos(photos)
        collectionDao.addCollections(collections)
        photoReactionsDao.addPhotoReactions(photoReactions)
        photoCollectionDao.addPhotosCollections(photosCollections)

        val result = photoFromCollectionDao.getPhotosFromCollection()
        Assert.assertTrue(result.isNotEmpty())

        photoReactionsDao.deletePhotoReactions()
        photoCollectionDao.deletePhotoCollection()
        reactionsTypeDao.deleteReactionsTypes()
        userDao.deleteUsers()
        collectionDao.deleteCollections()
        photoFromCollectionDao.deletePhotosFromCollections()

        val afterDelete = photoFromCollectionDao.getPhotosFromCollection()
        Assert.assertTrue(afterDelete.isEmpty())
    }


    @Test
    fun givenPhotoFeedExist_whenDeleted_thenTableIsEmpty() = runTest {
        val users = createUsersForFeed()
        val photos = createPhotosForFeed()
        val reactionsType = createReactionsTypeForFeed()
        val photoReactions = createPhotoReactionsForFeed()

        userDao.addUsers(users)
        reactionsTypeDao.addReactionsTypes(reactionsType)
        photoDao.addPhotos(photos)
        photoReactionsDao.addPhotoReactions(photoReactions)

        val result =  photoFeedDao.getFeedPhotos().load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 6,
                placeholdersEnabled = false
            )
        )
        val data = (result as PagingSource.LoadResult.Page).data
        Assert.assertTrue(data.isNotEmpty())

        photoDao.deleteFeedPhotos()
        reactionsTypeDao.deleteReactionsTypes()
        userDao.deleteUsers()

        val afterDelete = photoFeedDao.getFeedPhotos().load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 6,
                placeholdersEnabled = false
            )
        )
        val afterDeleteData = (afterDelete as PagingSource.LoadResult.Page).data
        Assert.assertTrue(afterDeleteData.isEmpty())
    }


    @Test
    fun givenPhotoSearchExist_whenDeleted_thenTableIsEmpty() = runTest {
        val users = createUsersForSearch()
        val photos = createPhotosForSearch()
        val reactionsType = createReactionsTypeForSearch()
        val photoReactions = createPhotoReactionsForSearch()

        userDao.addUsers(users)
        reactionsTypeDao.addReactionsTypes(reactionsType)
        photoDao.addPhotos(photos)
        photoReactionsDao.addPhotoReactions(photoReactions)

        val result = photoSearchDao.getSearchPhotos().load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 6,
                placeholdersEnabled = false
            )
        )
        val data = (result as PagingSource.LoadResult.Page).data
        Assert.assertTrue(data.isNotEmpty())

        photoDao.deleteSearchPhotos()

        val afterDelete = photoSearchDao.getSearchPhotos().load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 6,
                placeholdersEnabled = false
            )
        )
        val afterDeleteData = (afterDelete as PagingSource.LoadResult.Page).data
        Assert.assertTrue(afterDeleteData.isEmpty())
    }


}