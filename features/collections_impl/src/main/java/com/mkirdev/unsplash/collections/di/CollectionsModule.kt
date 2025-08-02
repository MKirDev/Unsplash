package com.mkirdev.unsplash.collections.di

import com.mkirdev.unsplash.collections.impl.CollectionsViewModelFactory
import com.mkirdev.unsplash.domain.usecases.collections.GetCollectionsUseCase
import dagger.Module
import dagger.Provides

@Module
internal class CollectionsModule {
    @Provides
    fun provideCollectionsViewModelFactory(
        getCollectionsUseCase: GetCollectionsUseCase
    ): CollectionsViewModelFactory {
        return CollectionsViewModelFactory(getCollectionsUseCase = getCollectionsUseCase)
    }
}