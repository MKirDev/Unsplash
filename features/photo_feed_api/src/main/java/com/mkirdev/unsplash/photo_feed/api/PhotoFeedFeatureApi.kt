package com.mkirdev.unsplash.photo_feed.api

import androidx.navigation.NavGraphBuilder

interface PhotoFeedFeatureApi {
    fun NavGraphBuilder.photoFeed(onNavigateToDetails: (String) -> Unit)
}