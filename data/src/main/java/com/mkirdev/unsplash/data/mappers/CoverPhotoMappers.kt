package com.mkirdev.unsplash.data.mappers

import com.mkirdev.unsplash.data.network.models.collections.CoverPhotoNetwork

internal fun CoverPhotoNetwork.toCoverPhotoUrl() = imageUrl.toRegular()