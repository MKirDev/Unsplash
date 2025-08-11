package com.mkirdev.unsplash.data.mappers

import com.mkirdev.unsplash.data.storages.database.dto.user.UserProfileDto
import com.mkirdev.unsplash.domain.models.CurrentUser

internal fun UserProfileDto.toDomain(): CurrentUser {
    return CurrentUser(
        id = profileId,
        username = username,
        name = name,
        firstName = firstName,
        lastName = lastName,
        imageUrl = imageUrl,
        totalLikes = totalLikes,
        bio = bio,
        location = location ,
        downloads = downloads,
        email = email
    )
}