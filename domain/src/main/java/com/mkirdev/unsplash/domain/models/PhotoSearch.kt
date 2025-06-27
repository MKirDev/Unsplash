package com.mkirdev.unsplash.domain.models

private const val EMPTY_STRING = ""
data class PhotoSearch(
    val search: String = EMPTY_STRING
)