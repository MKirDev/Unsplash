package com.mkirdev.unsplash.collections.di

import com.mkirdev.unsplash.domain.usecases.collections.GetCollectionsUseCase

interface CollectionsDependencies {
    val getCollectionsUseCase: GetCollectionsUseCase
}