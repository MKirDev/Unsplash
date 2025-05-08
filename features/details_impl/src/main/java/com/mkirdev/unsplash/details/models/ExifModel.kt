package com.mkirdev.unsplash.details.models

import androidx.compose.runtime.Immutable

@Immutable
data class ExifModel(
    val make: String,
    val model: String,
    val exposureTime: String,
    val aperture: String,
    val focalLength: String,
    val iso: String
)