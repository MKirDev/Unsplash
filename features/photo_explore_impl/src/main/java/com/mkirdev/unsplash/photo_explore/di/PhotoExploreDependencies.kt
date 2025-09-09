package com.mkirdev.unsplash.photo_explore.di

import com.mkirdev.unsplash.photo_feed.api.PhotoFeedFeatureApi
import com.mkirdev.unsplash.photo_search.api.PhotoSearchFeatureApi

interface PhotoExploreDependencies {

    val photoFeedFeatureApi: PhotoFeedFeatureApi

    val photoSearchFeatureApi: PhotoSearchFeatureApi
}