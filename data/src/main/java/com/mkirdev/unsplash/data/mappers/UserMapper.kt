package com.mkirdev.unsplash.data.mappers

import com.mkirdev.unsplash.data.network.photo.models.common.UserNetwork
import com.mkirdev.unsplash.data.storages.database.dto.base.UserDto

internal fun UserNetwork.toUserDto(): UserDto {
    return UserDto(
        id = id,
        fullName = name,
        username = username,
        imageUrl = imageUrl.toSmall(),
        bio = bio,
        location = location
    )
}