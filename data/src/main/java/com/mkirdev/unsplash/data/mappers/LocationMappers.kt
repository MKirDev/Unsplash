package com.mkirdev.unsplash.data.mappers

import com.mkirdev.unsplash.data.network.models.details.LocationNetwork
import com.mkirdev.unsplash.domain.models.Location

internal fun LocationNetwork.toDomain(): Location {
    return Location(
        name = name,
        city = city,
        coordinates = coordinates?.toDomain()
    )
}
