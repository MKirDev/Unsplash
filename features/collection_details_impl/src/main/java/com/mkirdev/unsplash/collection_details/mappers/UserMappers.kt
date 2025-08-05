package com.mkirdev.unsplash.collection_details.mappers

import com.mkirdev.unsplash.domain.models.User
import com.mkirdev.unsplash.photo_item.models.UserModel

internal fun User.toUsername() = username

internal fun User.toPresentation() = UserModel(
    name = name,
    username = username,
    userImage = imageUrl
)