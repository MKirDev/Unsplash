package com.mkirdev.unsplash.photo_feed.di

import com.mkirdev.unsplash.domain.repository.PhotosRepository
import com.mkirdev.unsplash.domain.usecases.photos.GetPhotosUseCase
import com.mkirdev.unsplash.domain.usecases.photos.LikePhotoLocalUseCase
import com.mkirdev.unsplash.domain.usecases.photos.SearchPhotosUseCase
import com.mkirdev.unsplash.domain.usecases.photos.UnlikePhotoLocalUseCase
import com.mkirdev.unsplash.photo_feed.impl.PhotoFeedViewModelFactory
import dagger.Component

@Component(modules = [PhotoFeedModule::class], dependencies = [PhotoFeedDependencies::class])
@PhotoFeedScope
internal interface PhotoFeedComponent : PhotoFeedDependencies {

    override val getPhotosUseCase: GetPhotosUseCase
    override val searchPhotosUseCase: SearchPhotosUseCase
    override val likePhotoLocalUseCase: LikePhotoLocalUseCase
    override val unlikePhotoLocalUseCase: UnlikePhotoLocalUseCase

    val photoFeedViewModelFactory: PhotoFeedViewModelFactory

    @Component.Builder
    interface Builder {
        fun addDependencies(dependencies: PhotoFeedDependencies): Builder

        fun build(): PhotoFeedComponent
    }
}