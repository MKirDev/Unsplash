package com.mkirdev.unsplash.collection_details.di

import com.mkirdev.unsplash.collection_details.impl.CollectionDetailsViewModelFactoryAssisted
import com.mkirdev.unsplash.domain.usecases.collections.GetCollectionInfoUseCase
import com.mkirdev.unsplash.domain.usecases.collections.GetCollectionPhotosUseCase
import com.mkirdev.unsplash.domain.usecases.photos.AddDownloadLinkUseCase
import com.mkirdev.unsplash.domain.usecases.photos.LikePhotoLocalUseCase
import com.mkirdev.unsplash.domain.usecases.photos.UnlikePhotoLocalUseCase
import dagger.Component

@Component(dependencies = [CollectionDetailsDependencies::class])
@CollectionDetailsScope
internal interface CollectionDetailsComponent : CollectionDetailsDependencies {

    val factoryAssisted: CollectionDetailsViewModelFactoryAssisted

    @Component.Builder
    interface Builder {
        fun addDependencies(dependencies: CollectionDetailsDependencies): Builder
        fun build(): CollectionDetailsComponent
    }

}