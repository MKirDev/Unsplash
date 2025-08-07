package com.mkirdev.unsplash.data.mappers

import com.mkirdev.unsplash.data.network.models.common.CurrentUserNetwork
import com.mkirdev.unsplash.domain.models.CurrentUser

internal fun CurrentUserNetwork.toDomain() = CurrentUser(
    id = id,
    username = username,
    name = name,
    firstName = firstName,
    lastName = lastName,
    imageUrl = imageUrl.toSmall(),
    totalLikes = totalLikes,
    bio = bio,
    location = location,
    downloads = downloads,
    email = email
)
