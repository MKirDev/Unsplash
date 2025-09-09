package com.mkirdev.unsplash.photo_search.di

import com.mkirdev.unsplash.domain.usecases.photos.LikePhotoLocalUseCase
import com.mkirdev.unsplash.domain.usecases.search.SearchPhotosUseCase
import com.mkirdev.unsplash.domain.usecases.photos.UnlikePhotoLocalUseCase

interface PhotoSearchDependencies {

    val searchPhotosUseCase: SearchPhotosUseCase
    val likePhotoLocalUseCase: LikePhotoLocalUseCase
    val unlikePhotoLocalUseCase: UnlikePhotoLocalUseCase
}