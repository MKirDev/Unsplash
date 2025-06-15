package com.mkirdev.unsplash.bottom_menu.di

import com.mkirdev.unsplash.bottom_menu.impl.BottomMenuViewModelFactory
import com.mkirdev.unsplash.core.navigation.TopDestinations
import com.mkirdev.unsplash.photo_feed.api.PhotoFeedFeatureApi
import dagger.Component

@Component(modules = [BottomMenuModule::class], dependencies = [BottomMenuDependencies::class])
@BottomMenuScope
internal interface BottomMenuComponent : BottomMenuDependencies {

    override val photoFeedFeatureApi: PhotoFeedFeatureApi
    override val topLevelDestination: TopDestinations
    val bottomMenuViewModelFactory: BottomMenuViewModelFactory

    @Component.Builder
    interface Builder {
        fun addDependencies(dependencies: BottomMenuDependencies): Builder
        fun build() : BottomMenuComponent
    }

}