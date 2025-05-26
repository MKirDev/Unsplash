package com.mkirdev.unsplash.data.network.auth.models

data class TokensNetwork(
    val accessToken: String,
    val refreshToken: String,
    val idToken: String
)
