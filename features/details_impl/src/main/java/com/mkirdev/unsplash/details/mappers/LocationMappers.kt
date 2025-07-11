package com.mkirdev.unsplash.details.mappers

import com.mkirdev.unsplash.details.models.LocationModel
import com.mkirdev.unsplash.domain.models.Location

internal fun Location.toLocationModel(): LocationModel =
    LocationModel(
        place = if (!name.isNullOrEmpty() && !city.isNullOrEmpty()) "$name, $city"
        else if (!name.isNullOrEmpty()) "$name"
        else if (!city.isNullOrEmpty()) "$city"
        else null,
        coordinatesModel = coordinates?.toCoordinatesModel()
    )