package com.mkirdev.unsplash.social_collections.api

import androidx.compose.runtime.Composable

class SocialCollectionsFeatureApiImpl : SocialCollectionsFeatureApi {
    @Composable
    override fun SocialCollectionsScreen(text: String): Unit = SocialCollectionsScreen(text = text)
}