package com.mkirdev.unsplash.details.mappers

import com.mkirdev.unsplash.domain.models.User
import com.mkirdev.unsplash.photo_item.models.UserModel

internal fun User.toUserModel(): UserModel {
    return UserModel(
        name = name,
        username = username,
        userImage = imageUrl
    )
}