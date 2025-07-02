package com.mkirdev.unsplash.photo_feed.di

import com.mkirdev.unsplash.domain.usecases.photos.GetPhotosUseCase
import com.mkirdev.unsplash.domain.usecases.photos.LikePhotoLocalUseCase
import com.mkirdev.unsplash.domain.usecases.photos.SearchPhotosUseCase
import com.mkirdev.unsplash.domain.usecases.photos.UnlikePhotoLocalUseCase
import com.mkirdev.unsplash.photo_feed.impl.PhotoFeedViewModelFactory
import dagger.Module
import dagger.Provides

@Module
internal class PhotoFeedModule {

    @Provides
    fun providePhotoFeedViewModelFactory(
        getPhotosUseCase: GetPhotosUseCase,
        likePhotoLocalUseCase: LikePhotoLocalUseCase,
        unlikePhotoLocalUseCase: UnlikePhotoLocalUseCase,
        searchPhotosUseCase: SearchPhotosUseCase
    ): PhotoFeedViewModelFactory {
        return PhotoFeedViewModelFactory(
            getPhotosUseCase = getPhotosUseCase,
            likePhotoLocalUseCase = likePhotoLocalUseCase,
            unlikePhotoLocalUseCase = unlikePhotoLocalUseCase,
            searchPhotosUseCase = searchPhotosUseCase
        )
    }
}