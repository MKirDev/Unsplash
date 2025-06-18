package com.mkirdev.unsplash.photo_feed.di

import com.mkirdev.unsplash.domain.repository.PhotosRepository
import com.mkirdev.unsplash.photo_feed.impl.PhotoFeedViewModelFactory
import dagger.Component

@Component(modules = [PhotoFeedModule::class], dependencies = [PhotoFeedDependencies::class])
@PhotoFeedScope
internal interface PhotoFeedComponent : PhotoFeedDependencies {

    override val photosRepository: PhotosRepository

    val photoFeedViewModelFactory: PhotoFeedViewModelFactory

    @Component.Builder
    interface Builder {
        fun addDependencies(dependencies: PhotoFeedDependencies): Builder

        fun build(): PhotoFeedComponent
    }
}