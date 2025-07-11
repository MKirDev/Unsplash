package com.mkirdev.unsplash.details.di

import com.mkirdev.unsplash.domain.repository.PhotosRepository
import com.mkirdev.unsplash.domain.usecases.photos.AddDownloadLinkUseCase
import com.mkirdev.unsplash.domain.usecases.photos.GetPhotoUseCase
import com.mkirdev.unsplash.domain.usecases.photos.LikePhotoLocalUseCase
import com.mkirdev.unsplash.domain.usecases.photos.UnlikePhotoLocalUseCase

interface DetailsDependencies {
    val getPhotoUseCase: GetPhotoUseCase
    val likePhotoLocalUseCase: LikePhotoLocalUseCase
    val unlikePhotoLocalUseCase: UnlikePhotoLocalUseCase
    val addDownloadLinkUseCase: AddDownloadLinkUseCase
}