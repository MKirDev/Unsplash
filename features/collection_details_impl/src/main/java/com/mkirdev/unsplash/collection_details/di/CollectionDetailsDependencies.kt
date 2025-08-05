package com.mkirdev.unsplash.collection_details.di

import com.mkirdev.unsplash.domain.usecases.collections.GetCollectionInfoUseCase
import com.mkirdev.unsplash.domain.usecases.collections.GetCollectionPhotosUseCase
import com.mkirdev.unsplash.domain.usecases.photos.AddDownloadLinkUseCase
import com.mkirdev.unsplash.domain.usecases.photos.LikePhotoLocalUseCase
import com.mkirdev.unsplash.domain.usecases.photos.UnlikePhotoLocalUseCase

interface CollectionDetailsDependencies {
    val getCollectionInfoUseCase: GetCollectionInfoUseCase
    val getCollectionPhotosUseCase: GetCollectionPhotosUseCase
    val likePhotoLocalUseCase: LikePhotoLocalUseCase
    val unlikePhotoLocalUseCase: UnlikePhotoLocalUseCase
    val addDownloadLinkUseCase: AddDownloadLinkUseCase
}