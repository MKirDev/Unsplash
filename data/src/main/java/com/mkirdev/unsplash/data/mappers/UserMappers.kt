package com.mkirdev.unsplash.data.mappers

import com.mkirdev.unsplash.data.network.models.common.UserNetwork
import com.mkirdev.unsplash.data.storages.database.dto.collection.UserCollectionDto
import com.mkirdev.unsplash.data.storages.database.dto.feed.UserFeedDto
import com.mkirdev.unsplash.data.storages.database.dto.liked.UserLikedDto
import com.mkirdev.unsplash.data.storages.database.dto.search.UserSearchDto
import com.mkirdev.unsplash.data.storages.database.entities.collection.UserCollectionEntity
import com.mkirdev.unsplash.data.storages.database.entities.feed.UserFeedEntity
import com.mkirdev.unsplash.data.storages.database.entities.liked.UserLikedEntity
import com.mkirdev.unsplash.data.storages.database.entities.search.UserSearchEntity
import com.mkirdev.unsplash.domain.models.User

private const val EMPTY_STRING = ""

internal fun UserNetwork.toUserFeedEntity(): UserFeedEntity {
    return UserFeedEntity(
        userId = id,
        fullName = name,
        username = username,
        imageUrl = imageUrl.toSmall(),
        bio = bio,
        location = location
    )
}

internal fun UserNetwork.toUserSearchEntity(): UserSearchEntity {
    return UserSearchEntity(
        userId = id,
        fullName = name,
        username = username,
        imageUrl = imageUrl.toSmall(),
        bio = bio,
        location = location
    )
}

internal fun UserNetwork.toUserCollectionEntity(): UserCollectionEntity {
    return UserCollectionEntity(
        userId = id,
        fullName = name,
        username = username,
        imageUrl = imageUrl.toSmall(),
        bio = bio,
        location = location
    )
}

internal fun UserNetwork.toUserLikedEntity(): UserLikedEntity {
    return UserLikedEntity(
        userId = id,
        fullName = name,
        username = username,
        imageUrl = imageUrl.toSmall(),
        bio = bio,
        location = location
    )
}

internal fun UserNetwork.toDomain(): User {
    return User(
        id = id,
        name = name,
        username = username,
        imageUrl = imageUrl.toSmall(),
        bio = bio ?: EMPTY_STRING,
        location = location ?: EMPTY_STRING
    )
}

internal fun UserFeedDto.toDomain(): User {
    return User(
        id = id,
        name = fullName,
        username = username,
        imageUrl = imageUrl,
        bio = bio ?: EMPTY_STRING,
        location = location ?: EMPTY_STRING
    )
}

internal fun UserSearchDto.toDomain(): User {
    return User(
        id = id,
        name = fullName,
        username = username,
        imageUrl = imageUrl,
        bio = bio ?: EMPTY_STRING,
        location = location ?: EMPTY_STRING
    )
}

internal fun UserCollectionDto.toDomain(): User {
    return User(
        id = id,
        name = fullName,
        username = username,
        imageUrl = imageUrl,
        bio = bio ?: EMPTY_STRING,
        location = location ?: EMPTY_STRING
    )
}

internal fun UserLikedDto.toDomain(): User {
    return User(
        id = id,
        name = fullName,
        username = username,
        imageUrl = imageUrl,
        bio = bio ?: EMPTY_STRING,
        location = location ?: EMPTY_STRING
    )
}