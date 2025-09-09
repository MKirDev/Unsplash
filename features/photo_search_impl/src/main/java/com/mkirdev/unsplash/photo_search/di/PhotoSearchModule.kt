package com.mkirdev.unsplash.photo_search.di

import com.mkirdev.unsplash.domain.usecases.photos.LikePhotoLocalUseCase
import com.mkirdev.unsplash.domain.usecases.search.SearchPhotosUseCase
import com.mkirdev.unsplash.domain.usecases.photos.UnlikePhotoLocalUseCase
import com.mkirdev.unsplash.photo_search.impl.PhotoSearchViewModelFactory
import dagger.Module
import dagger.Provides

@Module
internal class PhotoSearchModule {

    @Provides
    fun providePhotoSearchViewModelFactory(
        searchPhotosUseCase: SearchPhotosUseCase,
        likePhotoLocalUseCase: LikePhotoLocalUseCase,
        unlikePhotoLocalUseCase: UnlikePhotoLocalUseCase
    ): PhotoSearchViewModelFactory {
        return PhotoSearchViewModelFactory(
            searchPhotosUseCase = searchPhotosUseCase,
            likePhotoLocalUseCase = likePhotoLocalUseCase,
            unlikePhotoLocalUseCase = unlikePhotoLocalUseCase
        )
    }
}