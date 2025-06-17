package com.mkirdev.unsplash.data.mappers

import com.mkirdev.unsplash.data.network.photos.models.details.CoordinatesNetwork
import com.mkirdev.unsplash.domain.models.Coordinates

internal fun CoordinatesNetwork.toDomain(): Coordinates {
    return Coordinates(
        latitude = latitude,
        longitude = longitude
    )
}