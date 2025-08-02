package com.mkirdev.unsplash.collections.mappers

import com.mkirdev.unsplash.collection_item.models.CollectionUserModel
import com.mkirdev.unsplash.domain.models.User

internal fun User.toPresentation() =
    CollectionUserModel(
        name = name,
        username = username,
        userImage = imageUrl
    )