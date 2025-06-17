package com.mkirdev.unsplash.data.mappers

import com.mkirdev.unsplash.data.network.photos.models.details.LocationNetwork
import com.mkirdev.unsplash.domain.models.Location

private const val EMPTY_STRING = ""
internal fun LocationNetwork.toDomain(): Location {
    return Location(
        name = name ?: EMPTY_STRING,
        city = city ?: EMPTY_STRING,
        coordinates = coordinates.toDomain()
    )
}
