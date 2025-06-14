package com.mkirdev.unsplash.data.mappers

import com.mkirdev.unsplash.data.network.photos.models.common.UserNetwork
import com.mkirdev.unsplash.data.storages.database.dto.base.UserDto
import com.mkirdev.unsplash.data.storages.database.entities.UserEntity
import com.mkirdev.unsplash.domain.models.User

private const val EMPTY_STRING = ""

internal fun UserNetwork.toUserEntity(): UserEntity {
    return UserEntity(
        id = id,
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

internal fun UserDto.toDomain(): User {
    return User(
        id = id,
        name = fullName,
        username = username,
        imageUrl = imageUrl,
        bio = bio ?: EMPTY_STRING,
        location = location ?: EMPTY_STRING
    )
}