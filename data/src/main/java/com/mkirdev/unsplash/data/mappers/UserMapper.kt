package com.mkirdev.unsplash.data.mappers

import com.mkirdev.unsplash.data.network.photos.models.common.UserNetwork
import com.mkirdev.unsplash.data.storages.database.entities.UserEntity

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