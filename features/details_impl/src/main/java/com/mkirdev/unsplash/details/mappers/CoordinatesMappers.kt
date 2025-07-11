package com.mkirdev.unsplash.details.mappers

import com.mkirdev.unsplash.details.models.CoordinatesModel
import com.mkirdev.unsplash.domain.models.Coordinates

internal fun Coordinates.toCoordinatesModel(): CoordinatesModel? =
    if ((latitude == null || latitude == 0.0) || (latitude == null || latitude == 0.0) )
    null
else CoordinatesModel(
        latitude = latitude,
        longitude = longitude
    )