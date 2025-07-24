package com.mkirdev.unsplash.data.mappers

import com.mkirdev.unsplash.data.network.models.details.ExifNetwork
import com.mkirdev.unsplash.domain.models.Exif

private const val NULL_REPLACEMENT_INT = -1
internal fun ExifNetwork.toDomain(): Exif {
    return Exif(
        make = make,
        model = model,
        name = name,
        exposureTime = exposureTime,
        aperture = aperture,
        focalLength = focalLength,
        iso = iso?.toInt() ?: NULL_REPLACEMENT_INT
    )
}