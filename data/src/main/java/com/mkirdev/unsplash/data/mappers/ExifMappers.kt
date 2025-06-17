package com.mkirdev.unsplash.data.mappers

import com.mkirdev.unsplash.data.network.photos.models.details.ExifNetwork
import com.mkirdev.unsplash.domain.models.Exif

private const val EMPTY_STRING = ""
private const val NULL_REPLACEMENT_INT = -1
internal fun ExifNetwork.toDomain(): Exif {
    return Exif(
        make = make ?: EMPTY_STRING,
        model = model ?: EMPTY_STRING,
        name = name ?: EMPTY_STRING,
        exposureTime = exposureTime ?: EMPTY_STRING,
        aperture = aperture ?: EMPTY_STRING,
        focalLength = focalLength ?: EMPTY_STRING,
        iso = iso?.toInt() ?: NULL_REPLACEMENT_INT
    )
}