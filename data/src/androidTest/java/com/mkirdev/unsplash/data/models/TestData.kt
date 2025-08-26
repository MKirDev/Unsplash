package com.mkirdev.unsplash.data.models

import com.mkirdev.unsplash.data.storages.database.entities.feed.PhotoFeedEntity
import com.mkirdev.unsplash.data.storages.database.entities.feed.PhotoReactionsFeedEntity
import com.mkirdev.unsplash.data.storages.database.entities.feed.ReactionsFeedEntity
import com.mkirdev.unsplash.data.storages.database.entities.feed.RemoteKeysFeedEntity
import com.mkirdev.unsplash.data.storages.database.entities.feed.UserFeedEntity

// Feed
fun createUserFeed(id: String) = UserFeedEntity(
    userId = id,
    fullName = "fullName",
    username = "username",
    imageUrl = "imageUrl",
    bio = null,
    location = null
)

fun createPhotoFeed(photoId: String, userId: String) = PhotoFeedEntity(
    photoId = photoId,
    width = 1000,
    height = 2000,
    imageUrl = "example.com",
    downloadLink = "link",
    htmlLink = "link",
    likes = 0,
    userId = userId
)

fun createReactionsFeedType(photoId: String) = ReactionsFeedEntity(
    photoId = photoId,
    liked = 1
)

fun createPhotoReactionsFeed(photoId: String, reactionsId: String) = PhotoReactionsFeedEntity(
    photoId, reactionsId
)

fun createUsersForFeed() = listOf(
    UserFeedEntity(
        userId = "userId1",
        fullName = "fullName",
        username = "username",
        imageUrl = "imageUrl",
        bio = null,
        location = null
    ),
    UserFeedEntity(
        userId = "userId2",
        fullName = "fullName",
        username = "username",
        imageUrl = "imageUrl",
        bio = null,
        location = null
    ),
    UserFeedEntity(
        userId = "userId3",
        fullName = "fullName",
        username = "username",
        imageUrl = "imageUrl",
        bio = null,
        location = null
    ),
    UserFeedEntity(
        userId = "userId4",
        fullName = "fullName",
        username = "username",
        imageUrl = "imageUrl",
        bio = null,
        location = null
    ),
    UserFeedEntity(
        userId = "userId5",
        fullName = "fullName",
        username = "username",
        imageUrl = "imageUrl",
        bio = null,
        location = null
    ),
    UserFeedEntity(
        userId = "userId6",
        fullName = "fullName",
        username = "username",
        imageUrl = "imageUrl",
        bio = null,
        location = null
    )
)

fun createPhotosForFeed() = listOf(
    PhotoFeedEntity(
        photoId = "photoId1",
        width = 1000,
        height = 2000,
        imageUrl = "example.com",
        downloadLink = "link",
        htmlLink = "link",
        likes = 0,
        userId = "userId1"
    ),
    PhotoFeedEntity(
        photoId = "photoId2",
        width = 1000,
        height = 2000,
        imageUrl = "example.com",
        downloadLink = "link",
        htmlLink = "link",
        likes = 0,
        userId = "userId2",
    ),
    PhotoFeedEntity(
        photoId = "photoId3",
        width = 1000,
        height = 2000,
        imageUrl = "example.com",
        downloadLink = "link",
        htmlLink = "link",
        likes = 0,
        userId = "userId3"
    ),
    PhotoFeedEntity(
        photoId = "photoId4",
        width = 1000,
        height = 2000,
        imageUrl = "example.com",
        downloadLink = "link",
        htmlLink = "link",
        likes = 0,
        userId = "userId4"
    ),
    PhotoFeedEntity(
        photoId = "photoId5",
        width = 1000,
        height = 2000,
        imageUrl = "example.com",
        downloadLink = "link",
        htmlLink = "link",
        likes = 0,
        userId = "userId5"
    ),
    PhotoFeedEntity(
        photoId = "photoId6",
        width = 1000,
        height = 2000,
        imageUrl = "example.com",
        downloadLink = "link",
        htmlLink = "link",
        likes = 0,
        userId = "userId6"
    ),
)

fun createReactionsTypeForFeed() = listOf(
    ReactionsFeedEntity(photoId = "photoId1", liked = 0),
    ReactionsFeedEntity(photoId = "photoId2", liked = 0),
    ReactionsFeedEntity(photoId = "photoId3", liked = 0),
    ReactionsFeedEntity(photoId = "photoId4", liked = 0),
    ReactionsFeedEntity(photoId = "photoId5", liked = 0),
    ReactionsFeedEntity(photoId = "photoId6", liked = 0)
)

fun createPhotoReactionsForFeed() = listOf(
    PhotoReactionsFeedEntity("photoId1", "photoId1"),
    PhotoReactionsFeedEntity("photoId2", "photoId2"),
    PhotoReactionsFeedEntity("photoId3", "photoId3"),
    PhotoReactionsFeedEntity("photoId4", "photoId4"),
    PhotoReactionsFeedEntity("photoId5", "photoId5"),
    PhotoReactionsFeedEntity("photoId6", "photoId6"),
)

fun createRemoteKeysFeed() = listOf(
    RemoteKeysFeedEntity(photoId = "photoId1", prevPage = 1, nextPage = 2),
    RemoteKeysFeedEntity(photoId = "photoId2", prevPage = 1, nextPage = 2),
    RemoteKeysFeedEntity(photoId = "photoId3", prevPage = 1, nextPage = 2),
    RemoteKeysFeedEntity(photoId = "photoId4", prevPage = 1, nextPage = 2),
    RemoteKeysFeedEntity(photoId = "photoId5", prevPage = 1, nextPage = 2),
    RemoteKeysFeedEntity(photoId = "photoId6", prevPage = 1, nextPage = 2)
    )