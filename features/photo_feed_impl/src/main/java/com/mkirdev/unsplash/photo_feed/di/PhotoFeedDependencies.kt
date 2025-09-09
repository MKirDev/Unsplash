package com.mkirdev.unsplash.photo_feed.di

import com.mkirdev.unsplash.domain.usecases.photos.GetPhotosUseCase
import com.mkirdev.unsplash.domain.usecases.photos.LikePhotoLocalUseCase
import com.mkirdev.unsplash.domain.usecases.search.SearchPhotosUseCase
import com.mkirdev.unsplash.domain.usecases.photos.UnlikePhotoLocalUseCase

interface PhotoFeedDependencies {
    val getPhotosUseCase: GetPhotosUseCase
    val likePhotoLocalUseCase: LikePhotoLocalUseCase
    val unlikePhotoLocalUseCase: UnlikePhotoLocalUseCase
    val searchPhotosUseCase: SearchPhotosUseCase
}