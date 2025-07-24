package com.mkirdev.unsplash.data.mappers

import com.mkirdev.unsplash.data.network.models.details.CoordinatesNetwork
import com.mkirdev.unsplash.domain.models.Coordinates

internal fun CoordinatesNetwork.toDomain(): Coordinates {
    return Coordinates(
        latitude = if (latitude == 0.0) null else latitude,
        longitude = if (longitude == 0.0) null else longitude
    )
}