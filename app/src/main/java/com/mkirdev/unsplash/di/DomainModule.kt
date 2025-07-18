package com.mkirdev.unsplash.di

import com.mkirdev.unsplash.domain.repository.PhotosRepository
import com.mkirdev.unsplash.domain.usecases.photos.AddDownloadLinkUseCase
import com.mkirdev.unsplash.domain.usecases.photos.ClearPhotosStorageUseCase
import com.mkirdev.unsplash.domain.usecases.photos.GetDownloadLinkUseCase
import com.mkirdev.unsplash.domain.usecases.photos.GetLikedPhotoUseCase
import com.mkirdev.unsplash.domain.usecases.photos.GetPhotoUseCase
import com.mkirdev.unsplash.domain.usecases.photos.GetPhotosUseCase
import com.mkirdev.unsplash.domain.usecases.photos.GetUnlikedPhotoUseCase
import com.mkirdev.unsplash.domain.usecases.photos.LikePhotoLocalUseCase
import com.mkirdev.unsplash.domain.usecases.photos.LikePhotoRemoteUseCase
import com.mkirdev.unsplash.domain.usecases.photos.SearchPhotosUseCase
import com.mkirdev.unsplash.domain.usecases.photos.UnlikePhotoLocalUseCase
import com.mkirdev.unsplash.domain.usecases.photos.UnlikePhotoRemoteUseCase
import dagger.Module
import dagger.Provides

@Module
class DomainModule {
    @Provides
    fun provideClearPhotosStorageUseCase(photosRepository: PhotosRepository): ClearPhotosStorageUseCase {
        return ClearPhotosStorageUseCase(photosRepository)
    }

    @Provides
    fun provideGetLikedPhotoUseCase(photosRepository: PhotosRepository): GetLikedPhotoUseCase {
        return GetLikedPhotoUseCase(photosRepository)
    }

    @Provides
    fun provideGetUnlikedPhotoUseCase(photosRepository: PhotosRepository): GetUnlikedPhotoUseCase {
        return GetUnlikedPhotoUseCase(photosRepository)
    }

    @Provides
    fun provideGetPhotosUseCase(photosRepository: PhotosRepository): GetPhotosUseCase {
        return GetPhotosUseCase(photosRepository)
    }

    @Provides
    fun provideSearchPhotosUseCase(photosRepository: PhotosRepository): SearchPhotosUseCase {
        return SearchPhotosUseCase(photosRepository)
    }

    @Provides
    fun provideGetPhotoUseCase(photosRepository: PhotosRepository): GetPhotoUseCase {
        return GetPhotoUseCase(photosRepository)
    }

    @Provides
    fun provideLikePhotoLocalUseCase(photosRepository: PhotosRepository): LikePhotoLocalUseCase {
        return LikePhotoLocalUseCase(photosRepository)
    }

    @Provides
    fun provideLikePhotoRemoteUseCase(photosRepository: PhotosRepository): LikePhotoRemoteUseCase {
        return LikePhotoRemoteUseCase(photosRepository)
    }

    @Provides
    fun provideUnlikePhotoLocalUseCase(photosRepository: PhotosRepository): UnlikePhotoLocalUseCase {
        return UnlikePhotoLocalUseCase(photosRepository)
    }

    @Provides
    fun provideUnlikePhotoRemoteUseCase(photosRepository: PhotosRepository): UnlikePhotoRemoteUseCase {
        return UnlikePhotoRemoteUseCase(photosRepository)
    }

    @Provides
    fun provideAddDownloadLinkUseCase(photosRepository: PhotosRepository): AddDownloadLinkUseCase {
        return AddDownloadLinkUseCase(photosRepository)
    }

    @Provides
    fun provideGetDownloadLinkUseCase(photosRepository: PhotosRepository): GetDownloadLinkUseCase {
        return GetDownloadLinkUseCase(photosRepository)
    }

}