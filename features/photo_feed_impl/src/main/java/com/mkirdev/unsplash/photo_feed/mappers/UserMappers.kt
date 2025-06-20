package com.mkirdev.unsplash.photo_feed.mappers

import com.mkirdev.unsplash.domain.models.User
import com.mkirdev.unsplash.photo_item.models.UserModel

internal fun User.toPresentation(): UserModel {
    return UserModel(
        name = name,
        username = username,
        userImage = imageUrl
    )
}