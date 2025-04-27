package com.mkirdev.unsplash.content_creation.api

import androidx.compose.runtime.Composable

class ContentCreationFeatureApiImpl : ContentCreationFeatureApi {
    @Composable
    override fun ContentCreationScreen(text: String): Unit =
        ContentCreationScreen(text = text)
}