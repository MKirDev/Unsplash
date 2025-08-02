package com.mkirdev.unsplash.collections.di

import com.mkirdev.unsplash.collections.impl.CollectionsViewModelFactory
import com.mkirdev.unsplash.domain.usecases.collections.GetCollectionsUseCase
import dagger.Component

@Component(
    modules = [CollectionsModule::class],
    dependencies = [CollectionsDependencies::class]
)
@CollectionsScope
internal interface CollectionsComponent : CollectionsDependencies {

    override val getCollectionsUseCase: GetCollectionsUseCase

    val collectionsViewModelFactory: CollectionsViewModelFactory

    @Component.Builder
    interface Builder {
        fun addDependencies(dependencies: CollectionsDependencies): Builder
        fun build(): CollectionsComponent
    }
}