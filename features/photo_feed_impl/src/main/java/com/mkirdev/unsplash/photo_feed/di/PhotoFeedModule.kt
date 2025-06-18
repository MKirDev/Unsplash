package com.mkirdev.unsplash.photo_feed.di

import com.mkirdev.unsplash.domain.usecases.photos.GetPhotosUseCase
import com.mkirdev.unsplash.domain.usecases.photos.LikePhotoUseCase
import com.mkirdev.unsplash.domain.usecases.photos.SearchPhotosUseCase
import com.mkirdev.unsplash.domain.usecases.photos.UnlikePhotoUseCase
import com.mkirdev.unsplash.photo_feed.impl.PhotoFeedViewModelFactory
import dagger.Module
import dagger.Provides

@Module
internal class PhotoFeedModule {

    @Provides
    fun providePhotoFeedViewModelFactory(
        getPhotosUseCase: GetPhotosUseCase,
        likePhotoUseCase: LikePhotoUseCase,
        unlikePhotoUseCase: UnlikePhotoUseCase,
        searchPhotosUseCase: SearchPhotosUseCase
    ): PhotoFeedViewModelFactory {
        return PhotoFeedViewModelFactory(
            getPhotosUseCase = getPhotosUseCase,
            likePhotoUseCase = likePhotoUseCase,
            unlikePhotoUseCase = unlikePhotoUseCase,
            searchPhotosUseCase = searchPhotosUseCase
        )
    }
}