package com.mkirdev.unsplash.photo_feed.di

import com.mkirdev.unsplash.domain.repository.PhotosRepository

interface PhotoFeedDependencies {
    val photosRepository: PhotosRepository
}