package com.mkirdev.unsplash.profile.mappers

import com.mkirdev.unsplash.domain.models.User
import com.mkirdev.unsplash.photo_item.models.UserModel

internal fun User.toPresentation(): UserModel {
    return UserModel(
        name = name,
        username = username,
        userImage = imageUrl
    )
}