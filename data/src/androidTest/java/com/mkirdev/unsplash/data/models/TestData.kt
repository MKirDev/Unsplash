package com.mkirdev.unsplash.data.models

import com.mkirdev.unsplash.data.storages.database.entities.CollectionEntity
import com.mkirdev.unsplash.data.storages.database.entities.PhotoCollectionEntity
import com.mkirdev.unsplash.data.storages.database.entities.PhotoEntity
import com.mkirdev.unsplash.data.storages.database.entities.PhotoReactionsEntity
import com.mkirdev.unsplash.data.storages.database.entities.ReactionsTypeEntity
import com.mkirdev.unsplash.data.storages.database.entities.RemoteKeysFeedEntity
import com.mkirdev.unsplash.data.storages.database.entities.RemoteKeysSearchEntity
import com.mkirdev.unsplash.data.storages.database.entities.UserEntity

fun createSingleUser(id: String) = UserEntity(
    id = id,
    fullName = "fullName",
    username = "username",
    imageUrl = "imageUrl",
    bio = null,
    location = null
)

fun createSinglePhoto(photoId: String, userId: String) = PhotoEntity(
    id = photoId,
    width = 1000,
    height = 2000,
    imageUrl = "example.com",
    downloadLink = "link",
    htmlLink = "link",
    likes = 0,
    userId = userId,
    searchType = 0,
    position = 0
)

fun createSingleReactionsType(photoId: String) = ReactionsTypeEntity(
    id = photoId,
    liked = 1
)

fun createSinglePhotoReactions(photoId: String, reactionsId: String) = PhotoReactionsEntity(
    photoId, reactionsId
)

fun createUsersForCollections() = listOf(
    UserEntity(
        id = "userId1",
        fullName = "fullName",
        username = "username",
        imageUrl = "imageUrl",
        bio = null,
        location = null
    ),
    UserEntity(
        id = "userId2",
        fullName = "fullName",
        username = "username",
        imageUrl = "imageUrl",
        bio = null,
        location = null
    ),
    UserEntity(
        id = "userId3",
        fullName = "fullName",
        username = "username",
        imageUrl = "imageUrl",
        bio = null,
        location = null
    ),
    UserEntity(
        id = "userId4",
        fullName = "fullName",
        username = "username",
        imageUrl = "imageUrl",
        bio = null,
        location = null
    ),
    UserEntity(
        id = "userId5",
        fullName = "fullName",
        username = "username",
        imageUrl = "imageUrl",
        bio = null,
        location = null
    ),
    UserEntity(
        id = "userId6",
        fullName = "fullName",
        username = "username",
        imageUrl = "imageUrl",
        bio = null,
        location = null
    )
)

fun createPhotosForCollections() = listOf(
    PhotoEntity(
        id = "photoId1",
        width = 1000,
        height = 2000,
        imageUrl = "example.com",
        downloadLink = "link",
        htmlLink = "link",
        likes = 0,
        userId = "userId1",
        searchType = 0,
        position = 1
    ),
    PhotoEntity(
        id = "photoId2",
        width = 1000,
        height = 2000,
        imageUrl = "example.com",
        downloadLink = "link",
        htmlLink = "link",
        likes = 0,
        userId = "userId2",
        searchType = 0,
        position = 2
    ),
    PhotoEntity(
        id = "photoId3",
        width = 1000,
        height = 2000,
        imageUrl = "example.com",
        downloadLink = "link",
        htmlLink = "link",
        likes = 0,
        userId = "userId3",
        searchType = 0,
        position = 3
    ),
    PhotoEntity(
        id = "photoId4",
        width = 1000,
        height = 2000,
        imageUrl = "example.com",
        downloadLink = "link",
        htmlLink = "link",
        likes = 0,
        userId = "userId4",
        searchType = 0,
        position = 4
    ),
    PhotoEntity(
        id = "photoId5",
        width = 1000,
        height = 2000,
        imageUrl = "example.com",
        downloadLink = "link",
        htmlLink = "link",
        likes = 0,
        userId = "userId5",
        searchType = 0,
        position = 5
    ),
    PhotoEntity(
        id = "photoId6",
        width = 1000,
        height = 2000,
        imageUrl = "example.com",
        downloadLink = "link",
        htmlLink = "link",
        likes = 0,
        userId = "userId6",
        searchType = 0,
        position = 6
    ),
)

fun createCollections() = listOf(
    CollectionEntity(id = "collectionId1", name = "Test Collection1"),
    CollectionEntity(id = "collectionId2", name = "Test Collection2"),
    CollectionEntity(id = "collectionId3", name = "Test Collection3"),
    CollectionEntity(id = "collectionId4", name = "Test Collection4"),
    CollectionEntity(id = "collectionId5", name = "Test Collection5"),
    CollectionEntity(id = "collectionId6", name = "Test Collection7"),
)

fun createPhotoCollections() = listOf(
    PhotoCollectionEntity("photoId1", "collectionId1"),
    PhotoCollectionEntity("photoId2", "collectionId2"),
    PhotoCollectionEntity("photoId3", "collectionId3"),
    PhotoCollectionEntity("photoId4", "collectionId4"),
    PhotoCollectionEntity("photoId5", "collectionId5"),
    PhotoCollectionEntity("photoId6", "collectionId6")
)

fun createReactionsTypeForCollections() = listOf(
    ReactionsTypeEntity(id = "photoId1", liked = 0),
    ReactionsTypeEntity(id = "photoId2", liked = 0),
    ReactionsTypeEntity(id = "photoId3", liked = 0),
    ReactionsTypeEntity(id = "photoId4", liked = 0),
    ReactionsTypeEntity(id = "photoId5", liked = 0),
    ReactionsTypeEntity(id = "photoId6", liked = 0)
)

fun createPhotoReactionsForCollections() = listOf(
    PhotoReactionsEntity("photoId1", "photoId1"),
    PhotoReactionsEntity("photoId2", "photoId2"),
    PhotoReactionsEntity("photoId3", "photoId3"),
    PhotoReactionsEntity("photoId4", "photoId4"),
    PhotoReactionsEntity("photoId5", "photoId5"),
    PhotoReactionsEntity("photoId6", "photoId6"),
)

fun createUsersForFeed() = listOf(
    UserEntity(
        id = "userId1",
        fullName = "fullName",
        username = "username",
        imageUrl = "imageUrl",
        bio = null,
        location = null
    ),
    UserEntity(
        id = "userId2",
        fullName = "fullName",
        username = "username",
        imageUrl = "imageUrl",
        bio = null,
        location = null
    ),
    UserEntity(
        id = "userId3",
        fullName = "fullName",
        username = "username",
        imageUrl = "imageUrl",
        bio = null,
        location = null
    ),
    UserEntity(
        id = "userId4",
        fullName = "fullName",
        username = "username",
        imageUrl = "imageUrl",
        bio = null,
        location = null
    ),
    UserEntity(
        id = "userId5",
        fullName = "fullName",
        username = "username",
        imageUrl = "imageUrl",
        bio = null,
        location = null
    ),
    UserEntity(
        id = "userId6",
        fullName = "fullName",
        username = "username",
        imageUrl = "imageUrl",
        bio = null,
        location = null
    )
)

fun createPhotosForFeed() = listOf(
    PhotoEntity(
        id = "photoId1",
        width = 1000,
        height = 2000,
        imageUrl = "example.com",
        downloadLink = "link",
        htmlLink = "link",
        likes = 0,
        userId = "userId1",
        searchType = 0,
        position = 1
    ),
    PhotoEntity(
        id = "photoId2",
        width = 1000,
        height = 2000,
        imageUrl = "example.com",
        downloadLink = "link",
        htmlLink = "link",
        likes = 0,
        userId = "userId2",
        searchType = 0,
        position = 2
    ),
    PhotoEntity(
        id = "photoId3",
        width = 1000,
        height = 2000,
        imageUrl = "example.com",
        downloadLink = "link",
        htmlLink = "link",
        likes = 0,
        userId = "userId3",
        searchType = 0,
        position = 3
    ),
    PhotoEntity(
        id = "photoId4",
        width = 1000,
        height = 2000,
        imageUrl = "example.com",
        downloadLink = "link",
        htmlLink = "link",
        likes = 0,
        userId = "userId4",
        searchType = 0,
        position = 4
    ),
    PhotoEntity(
        id = "photoId5",
        width = 1000,
        height = 2000,
        imageUrl = "example.com",
        downloadLink = "link",
        htmlLink = "link",
        likes = 0,
        userId = "userId5",
        searchType = 0,
        position = 5
    ),
    PhotoEntity(
        id = "photoId6",
        width = 1000,
        height = 2000,
        imageUrl = "example.com",
        downloadLink = "link",
        htmlLink = "link",
        likes = 0,
        userId = "userId6",
        searchType = 0,
        position = 6
    ),
)

fun createReactionsTypeForFeed() = listOf(
    ReactionsTypeEntity(id = "photoId1", liked = 0),
    ReactionsTypeEntity(id = "photoId2", liked = 0),
    ReactionsTypeEntity(id = "photoId3", liked = 0),
    ReactionsTypeEntity(id = "photoId4", liked = 0),
    ReactionsTypeEntity(id = "photoId5", liked = 0),
    ReactionsTypeEntity(id = "photoId6", liked = 0)
)

fun createPhotoReactionsForFeed() = listOf(
    PhotoReactionsEntity("photoId1", "photoId1"),
    PhotoReactionsEntity("photoId2", "photoId2"),
    PhotoReactionsEntity("photoId3", "photoId3"),
    PhotoReactionsEntity("photoId4", "photoId4"),
    PhotoReactionsEntity("photoId5", "photoId5"),
    PhotoReactionsEntity("photoId6", "photoId6"),
)

fun createRemoteKeysFeed() = listOf(
    RemoteKeysFeedEntity(id = "photoId1", 1, 2),
    RemoteKeysFeedEntity(id = "photoId2", 1, 2),
    RemoteKeysFeedEntity(id = "photoId3", 1, 2),
    RemoteKeysFeedEntity(id = "photoId4", 1, 2),
    RemoteKeysFeedEntity(id = "photoId5", 1, 2),
    RemoteKeysFeedEntity(id = "photoId6", 1, 2)
    )

fun createUsersForSearch() = listOf(
    UserEntity(
        id = "userId1",
        fullName = "fullName",
        username = "username",
        imageUrl = "imageUrl",
        bio = null,
        location = null
    ),
    UserEntity(
        id = "userId2",
        fullName = "fullName",
        username = "username",
        imageUrl = "imageUrl",
        bio = null,
        location = null
    ),
    UserEntity(
        id = "userId3",
        fullName = "fullName",
        username = "username",
        imageUrl = "imageUrl",
        bio = null,
        location = null
    ),
    UserEntity(
        id = "userId4",
        fullName = "fullName",
        username = "username",
        imageUrl = "imageUrl",
        bio = null,
        location = null
    ),
    UserEntity(
        id = "userId5",
        fullName = "fullName",
        username = "username",
        imageUrl = "imageUrl",
        bio = null,
        location = null
    ),
    UserEntity(
        id = "userId6",
        fullName = "fullName",
        username = "username",
        imageUrl = "imageUrl",
        bio = null,
        location = null
    )
)

fun createPhotosForSearch() = listOf(
    PhotoEntity(
        id = "photoId1",
        width = 1000,
        height = 2000,
        imageUrl = "example.com",
        downloadLink = "link",
        htmlLink = "link",
        likes = 0,
        userId = "userId1",
        searchType = 1,
        position = 1
    ),
    PhotoEntity(
        id = "photoId2",
        width = 1000,
        height = 2000,
        imageUrl = "example.com",
        downloadLink = "link",
        htmlLink = "link",
        likes = 0,
        userId = "userId2",
        searchType = 1,
        position = 2
    ),
    PhotoEntity(
        id = "photoId3",
        width = 1000,
        height = 2000,
        imageUrl = "example.com",
        downloadLink = "link",
        htmlLink = "link",
        likes = 0,
        userId = "userId3",
        searchType = 1,
        position = 3
    ),
    PhotoEntity(
        id = "photoId4",
        width = 1000,
        height = 2000,
        imageUrl = "example.com",
        downloadLink = "link",
        htmlLink = "link",
        likes = 0,
        userId = "userId4",
        searchType = 1,
        position = 4
    ),
    PhotoEntity(
        id = "photoId5",
        width = 1000,
        height = 2000,
        imageUrl = "example.com",
        downloadLink = "link",
        htmlLink = "link",
        likes = 0,
        userId = "userId5",
        searchType = 1,
        position = 5
    ),
    PhotoEntity(
        id = "photoId6",
        width = 1000,
        height = 2000,
        imageUrl = "example.com",
        downloadLink = "link",
        htmlLink = "link",
        likes = 0,
        userId = "userId6",
        searchType = 1,
        position = 6
    ),
)

fun createReactionsTypeForSearch() = listOf(
    ReactionsTypeEntity(id = "photoId1", liked = 0),
    ReactionsTypeEntity(id = "photoId2", liked = 0),
    ReactionsTypeEntity(id = "photoId3", liked = 0),
    ReactionsTypeEntity(id = "photoId4", liked = 0),
    ReactionsTypeEntity(id = "photoId5", liked = 0),
    ReactionsTypeEntity(id = "photoId6", liked = 0)
)

fun createPhotoReactionsForSearch() = listOf(
    PhotoReactionsEntity("photoId1", "photoId1"),
    PhotoReactionsEntity("photoId2", "photoId2"),
    PhotoReactionsEntity("photoId3", "photoId3"),
    PhotoReactionsEntity("photoId4", "photoId4"),
    PhotoReactionsEntity("photoId5", "photoId5"),
    PhotoReactionsEntity("photoId6", "photoId6"),
)


fun createRemoteKeysSearch() = listOf(
    RemoteKeysSearchEntity(id = "photoId1", 3, 4),
    RemoteKeysSearchEntity(id = "photoId2", 3, 4),
    RemoteKeysSearchEntity(id = "photoId3", 3, 4),
    RemoteKeysSearchEntity(id = "photoId4", 3, 4),
    RemoteKeysSearchEntity(id = "photoId5", 3, 4),
    RemoteKeysSearchEntity(id = "photoId6", 3, 4)
)