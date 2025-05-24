package com.mkirdev.unsplash.content_creation.api

import androidx.compose.runtime.Composable
import com.mkirdev.unsplash.content_creation.impl.ContentCreationScreen
import javax.inject.Inject

class ContentCreationFeatureApiImpl @Inject constructor() : ContentCreationFeatureApi {
    @Composable
    override fun ContentCreationFeature(text: String): Unit =
        ContentCreationScreen(text = text)
}