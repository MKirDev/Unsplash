package com.mkirdev.unsplash.domain.models

data class Location(
    val name: String,
    val city: String,
    val latitude: Double,
    val longitude: Double,
)