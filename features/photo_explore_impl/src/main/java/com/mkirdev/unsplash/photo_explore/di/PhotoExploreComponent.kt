package com.mkirdev.unsplash.photo_explore.di

import com.mkirdev.unsplash.photo_feed.api.PhotoFeedFeatureApi
import com.mkirdev.unsplash.photo_search.api.PhotoSearchFeatureApi
import dagger.Component

@Component(dependencies = [PhotoExploreDependencies::class])
@PhotoExploreScope
internal interface PhotoExploreComponent : PhotoExploreDependencies {
    override val photoFeedFeatureApi: PhotoFeedFeatureApi

    override val photoSearchFeatureApi: PhotoSearchFeatureApi

    @Component.Builder
    interface Builder {
        fun addDependencies(dependencies: PhotoExploreDependencies): Builder
        fun build(): PhotoExploreComponent
    }
}