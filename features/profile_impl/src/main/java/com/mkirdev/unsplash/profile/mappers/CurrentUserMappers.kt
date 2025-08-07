package com.mkirdev.unsplash.profile.mappers

import com.mkirdev.unsplash.domain.models.CurrentUser
import com.mkirdev.unsplash.profile.models.ProfileModel

private const val EMPTY_STRING = ""

internal fun CurrentUser.toPresentation() =
    ProfileModel(
        id = id,
        userImage = imageUrl,
        username = username,
        fullName = name,
        bio = bio,
        location = location,
        totalLikes = if (totalLikes == 0) null else totalLikes.toString(),
        downloads = downloads.toString(),
        email = email
    )