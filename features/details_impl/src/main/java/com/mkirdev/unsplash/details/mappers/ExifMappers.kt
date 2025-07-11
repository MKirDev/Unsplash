package com.mkirdev.unsplash.details.mappers

import com.mkirdev.unsplash.details.models.ExifModel
import com.mkirdev.unsplash.domain.models.Exif


private const val NULL_REPLACEMENT_INT = -1
private const val UNKNOWN = "unknown"

internal fun Exif.toExifModel() = ExifModel(
    make = make ?: UNKNOWN,
    model = model ?: UNKNOWN,
    exposureTime = exposureTime ?: UNKNOWN,
    aperture = aperture ?: UNKNOWN,
    focalLength = focalLength ?: UNKNOWN,
    iso = if (iso == NULL_REPLACEMENT_INT) UNKNOWN else iso.toString()
)